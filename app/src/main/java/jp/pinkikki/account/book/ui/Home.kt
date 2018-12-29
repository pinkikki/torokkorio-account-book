package jp.pinkikki.account.book.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class Home : Fragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val accountBookService: AccountBookService by instance()

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val sum = view.findViewById<TextView>(R.id.sum_item_amount)

        accountBookService
            .list()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                sum.text =
                        "${getString(R.string.sum_item_amount_prefix)}${getString(R.string.en)}${Integer.toString(
                            list.sumBy { Integer.parseInt(it.amount) })}"
            }
        return view
    }
}