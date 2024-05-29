import { INestApplication } from '@nestjs/common';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';
import { AuthModule } from '../../api/auth/auth.module';
import { UserModule } from '../../api/user/user.module';
import { ProductModule } from '../../api/product/product.module';
import { TransactionModule } from '../../api/transaction/transaction.module';

export const setSwagger = (app: INestApplication) => {
  const config = new DocumentBuilder()
    .setTitle('wanted-pre-20')
    .setDescription('wanted-pre-20')
    .setVersion('1.0.0')
    .build();

  const document = SwaggerModule.createDocument(app, config, {
    include: [AuthModule, UserModule, ProductModule, TransactionModule],
  });

  SwaggerModule.setup('api', app, document, {
    swaggerOptions: {
      persistAuthorization: true,
      tagsSorter: 'alpha',
    },
  });
};
