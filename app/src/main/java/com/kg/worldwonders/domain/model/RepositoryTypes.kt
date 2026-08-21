package com.kg.worldwonders.domain.model

enum class CategoryOperation(
    val value: String
)
{
    AND("and"), OR("or")
}

enum class SortKey(val value: String)
{
    POPULARITY("popularity"),
    CREATED_ON("createdOn")
}

enum class SortDirection(val value: String)
{
    ASC("asc"), DESC("desc")
}