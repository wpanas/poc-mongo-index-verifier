package com.github.wpanas.poc.query

import org.bson.codecs.pojo.PojoCodecProvider
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty

data class ExplainedPlan @BsonCreator constructor(
    @BsonProperty("queryPlanner") private val queryPlanner: QueryPlanner
) {
    data class QueryPlanner @BsonCreator constructor(
        @BsonProperty("winningPlan") val winningPlan: WinningPlan
    ) {
        data class WinningPlan @BsonCreator constructor(
            @BsonProperty("stage") val stage: String
        )
    }

    fun isNotUsingIndex(): Boolean = queryPlanner.winningPlan.stage in NO_INDEX_WINNING_PLANS

    companion object {
        val NO_INDEX_WINNING_PLANS = setOf("COLLSCAN", "SORT")

        fun codecProvider() = PojoCodecProvider.builder()
            .register(ExplainedPlan::class.java)
            .register(ExplainedPlan.QueryPlanner::class.java)
            .register(ExplainedPlan.QueryPlanner.WinningPlan::class.java)
            .build()
    }
}