package jp.pinkikki.account.book.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.pinkikki.account.book.R
import jp.pinkikki.account.book.port.adapter.AccountBookService
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.time.format.DateTimeFormatter

class ItemList : Fragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val accountBookService: AccountBookService by instance()

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val sum = view.findViewById<TextView>(R.id.sum_item_amount)
        val itemList = view.findViewById<ListView>(R.id.item_list)
        val itemHeader = inflater.inflate(R.layout.item_header, container, false)

        itemList.addHeaderView(itemHeader, null, false)

        accountBookService
            .list()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                val row = list.map {
                    ItemRow(
                        it.name,
                        "${getString(R.string.en)}${it.amount}",
                        it.date.format(DateTimeFormatter.ISO_DATE)
                    )
                }
                itemList.adapter = ItemListAdapter(this.requireContext(), row)

                sum.text = "${getString(R.string.sum_item_amount_prefix)}${getString(R.string.en)}${Integer.toString(
                    list.sumBy { Integer.parseInt(it.amount) })}"
            }
        return view
    }
}