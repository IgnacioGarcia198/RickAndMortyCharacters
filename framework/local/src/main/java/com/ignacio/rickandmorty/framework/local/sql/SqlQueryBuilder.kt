package com.ignacio.rickandmorty.framework.local.sql

interface SqlQueryBuilder {
    fun buildWhereQuery(
        queryStart: String,
        vararg whereSegments: SqlQuerySegment,
    ): SqlQueryData

    fun buildWhereQuery(
        queryStart: String,
        whereSegments: List<SqlQuerySegment>,
    ): SqlQueryData

    data class SqlQueryData(
        val sqlText: String,
        val args: List<String> = emptyList(),
    )

    data class SqlQuerySegment(
        val sqlText: String,
        val arg: Any? = null,
    ) {
        init {
            if (sqlText.contains('?') && arg == null) {
                throw IllegalArgumentException("missing argument for $sqlText")
            } else if (arg != null && !sqlText.contains('?')) {
                throw IllegalArgumentException("unexpected argument $arg for $sqlText")
            }
        }
    }
}
