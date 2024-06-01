from flask import Flask, request, jsonify, abort
from flask_sqlalchemy import SQLAlchemy
from flask_marshmallow import Marshmallow
from flask_bcrypt import Bcrypt
from flask_jwt_extended import JWTManager, create_access_token, jwt_required, get_jwt_identity
from marshmallow_sqlalchemy import SQLAlchemySchema, auto_field

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///wanted_market.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['JWT_SECRET_KEY'] = '쉿 비밀입니다.' 

db = SQLAlchemy(app)
ma = Marshmallow(app)
bcrypt = Bcrypt(app)
jwt = JWTManager(app)

# User model
class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(150), unique=True, nullable=False)
    password = db.Column(db.String(150), nullable=False)

# Product model
class Product(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(150), nullable=False)
    price = db.Column(db.Float, nullable=False)
    status = db.Column(db.String(50), default='판매중')
    seller_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)
    buyer_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=True)

# Schemas
class UserSchema(SQLAlchemySchema):
    class Meta:
        model = User
        load_instance = True

    id = auto_field()
    username = auto_field()

class ProductSchema(SQLAlchemySchema):
    class Meta:
        model = Product
        load_instance = True

    id = auto_field()
    name = auto_field()
    price = auto_field()
    status = auto_field()
    seller_id = auto_field()
    buyer_id = auto_field()

user_schema = UserSchema()
users_schema = UserSchema(many=True)
product_schema = ProductSchema()
products_schema = ProductSchema(many=True)

# Create user
@app.route('/register', methods=['POST'])
def register():
    username = request.json.get('username')
    password = request.json.get('password')

    if User.query.filter_by(username=username).first():
        return jsonify({"msg": "Username already exists"}), 400

    hashed_password = bcrypt.generate_password_hash(password).decode('utf-8')
    new_user = User(username=username, password=hashed_password)
    db.session.add(new_user)
    db.session.commit()

    return jsonify(user_schema.dump(new_user))

# Login user
@app.route('/login', methods=['POST'])
def login():
    username = request.json.get('username')
    password = request.json.get('password')

    user = User.query.filter_by(username=username).first()
    if user and bcrypt.check_password_hash(user.password, password):
        access_token = create_access_token(identity=user.id)
        return jsonify(access_token=access_token)

    return jsonify({"msg": "Bad username or password"}), 401

# Create product
@app.route('/product', methods=['POST'])
@jwt_required()
def create_product():
    current_user_id = get_jwt_identity()
    name = request.json.get('name')
    price = request.json.get('price')

    new_product = Product(name=name, price=price, seller_id=current_user_id)
    db.session.add(new_product)
    db.session.commit()

    return jsonify(product_schema.dump(new_product))

# Get all products
@app.route('/products', methods=['GET'])
def get_products():
    products = Product.query.all()
    return jsonify(products_schema.dump(products))

# Get product by ID
@app.route('/product/<int:id>', methods=['GET'])
def get_product(id):
    product = Product.query.get_or_404(id)
    return jsonify(product_schema.dump(product))

# Buy product
@app.route('/product/<int:id>/buy', methods=['POST'])
@jwt_required()
def buy_product(id):
    product = Product.query.get_or_404(id)
    current_user_id = get_jwt_identity()

    if product.status != '판매중':
        return jsonify({"msg": "Product not available for purchase"}), 400

    product.status = '예약중'
    product.buyer_id = current_user_id
    db.session.commit()

    return jsonify(product_schema.dump(product))

# Get user transactions
@app.route('/my_transactions', methods=['GET'])
@jwt_required()
def my_transactions():
    current_user_id = get_jwt_identity()

    bought_products = Product.query.filter_by(buyer_id=current_user_id).all()
    reserved_products = Product.query.filter((Product.seller_id == current_user_id) | (Product.buyer_id == current_user_id)).filter_by(status='예약중').all()

    return jsonify({
        'bought_products': products_schema.dump(bought_products),
        'reserved_products': products_schema.dump(reserved_products)
    })

# Approve sale
@app.route('/product/<int:id>/approve', methods=['POST'])
@jwt_required()
def approve_sale(id):
    product = Product.query.get_or_404(id)
    current_user_id = get_jwt_identity()

    if product.seller_id != current_user_id:
        return jsonify({"msg": "Not authorized to approve this sale"}), 403

    if product.status != '예약중':
        return jsonify({"msg": "Product is not in reserved state"}), 400

    product.status = '완료'
    db.session.commit()

    return jsonify(product_schema.dump(product))

if __name__ == '__main__':
    with app.app_context():
        db.create_all()
    app.run(debug=True)
