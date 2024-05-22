package wanted.market

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import wanted.market.product.Product

@SpringBootApplication
class MarketApplication

fun main(args: Array<String>) {
	runApplication<MarketApplication>(*args)
}
