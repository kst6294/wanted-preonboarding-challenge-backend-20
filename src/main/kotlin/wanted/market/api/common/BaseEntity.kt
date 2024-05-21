package wanted.market.api.common

import org.springframework.data.domain.AbstractAggregateRoot

abstract class BaseEntity<ENTITY, ID> : AbstractAggregateRoot<BaseEntity<ENTITY, ID>?>()
