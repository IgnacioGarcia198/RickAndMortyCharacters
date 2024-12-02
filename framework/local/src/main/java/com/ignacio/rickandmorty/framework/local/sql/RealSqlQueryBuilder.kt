package com.ignacio.rickandmorty.framework.local.sql

import javax.inject.Inject

class RealSqlQueryBuilder @Inject constructor() : SqlQueryBuilder {
    override fun buildWhereQuery(
        queryStart: String,
        vararg whereSegments: SqlQueryBuilder.SqlQuerySegment,
    ): SqlQueryBuilder.SqlQueryData {
        return buildWhereQuery(queryStart, whereSegments.toList())
    }

    override fun buildWhereQuery(
        queryStart: String,
        whereSegments: List<SqlQueryBuilder.SqlQuerySegment>,
    ): SqlQueryBuilder.SqlQueryData {
        val sql = StringBuilder(queryStart)
        val args = mutableListOf<String>()
        whereSegments.forEach { segment ->
            sql.appendWhereQuerySegment(segment.sqlText)
            segment.arg?.let { args.add(it.toString()) }
        }
        return SqlQueryBuilder.SqlQueryData(sqlText = sql.toString(), args = args)
    }

    private fun StringBuilder.appendWhereQuerySegment(segment: String) {
        if (!this.contains("WHERE")) {
            this.append(" WHERE ")
        } else {
            this.append(" AND ")
        }
        this.append(segment)
    }
}

fun MutableList<SqlQueryBuilder.SqlQuerySegment>.addSegment(
    sqlText: String,
    arg: Any? = null,
) {
    add(SqlQueryBuilder.SqlQuerySegment(sqlText, arg))
}
