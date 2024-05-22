package wanted.market.product

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import wanted.market.member.Member

@RestController
class ProductController {

    @GetMapping("/")
    fun blog(member: Member): String {
        return "blog"
    }
}