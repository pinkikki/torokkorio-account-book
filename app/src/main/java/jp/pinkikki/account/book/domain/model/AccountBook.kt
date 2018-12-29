package jp.pinkikki.account.book.domain.model

import java.time.LocalDateTime

data class AccountBook(val name: String, val amount: String, val date: LocalDateTime)