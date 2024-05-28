package wanted.market

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class MarketApplication

fun main(args: Array<String>) {
	runApplication<MarketApplication>(*args)
}
