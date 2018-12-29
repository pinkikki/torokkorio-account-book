package jp.pinkikki.account.book.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.pinkikki.account.book.R
import jp.pinkikki.account.book.domain.model.AccountBook
import jp.pinkikki.account.book.port.adapter.AccountBookService
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.time.LocalDateTime

class ItemSave : Fragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val accountBookService: AccountBookService by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_save, container, false)

        val apiKey = resources.getString(R.string.api_key)

        val purchaseButton = view.findViewById<Button>(R.id.purchase_button)
        val nameText = view.findViewById<EditText>(R.id.name_text)
        val amountText = view.findViewById<EditText>(R.id.amount_text)
        val dateText = view.findViewById<EditText>(R.id.purchase_date_text)
        purchaseButton.setOnClickListener {
            accountBookService
                .register(
                    AccountBook(
                        nameText.text.toString(),
                        amountText.text.toString(),
                        LocalDateTime.parse(dateText.text.toString())
                    ), apiKey
                )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    nameText.text.clear()
                    amountText.text.clear()
                }
        }

        return view
    }
}