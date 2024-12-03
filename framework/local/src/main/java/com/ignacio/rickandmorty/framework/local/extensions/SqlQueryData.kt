package com.ignacio.rickandmorty.framework.local.extensions

import androidx.sqlite.db.SimpleSQLiteQuery
import com.ignacio.rickandmorty.framework.local.sql.SqlQueryBuilder

fun SqlQueryBuilder.SqlQueryData.toSupportSQLiteQuery() =
    SimpleSQLiteQuery(sqlText, args.toTypedArray())
