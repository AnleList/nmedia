package ru.netology.nmedia.db

import android.database.Cursor
import ru.netology.nmedia.data.Post

fun Cursor.toPost() = Post(
    id = getLong(getColumnIndexOrThrow(PostsTable.Column.ID.columnName)),
    author = getString(getColumnIndexOrThrow(PostsTable.Column.AUTHOR.columnName)),
    textContent = getString(getColumnIndexOrThrow(PostsTable.Column.TEXT_CONTENT.columnName)),
    draftTextContent = getString(
        getColumnIndexOrThrow(PostsTable.Column.DRAFT_TEXT_CONTENT.columnName)
    ),
    published = getString(getColumnIndexOrThrow(PostsTable.Column.PUBLISHED.columnName)),
    videoContent = getString(getColumnIndexOrThrow(PostsTable.Column.VIDEO_CONTENT.columnName)),
    likes = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKES.columnName)),
    likedByMe = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKED_BY_ME.columnName)) != 0
    )