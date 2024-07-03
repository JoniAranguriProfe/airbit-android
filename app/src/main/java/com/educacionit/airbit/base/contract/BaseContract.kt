package com.educacionit.airbit.base.contract

import android.content.Context
import android.widget.Toast

interface BaseContract {
    interface BaseView {
        fun showErrorMessage(error: String) {
            Toast.makeText(getViewContext(), error, Toast.LENGTH_SHORT).show()
        }

        fun getViewContext(): Context
        fun initPresenter()
        fun showNoInternetMessage()

    }

    interface BasePresenter<T : BaseView> {
        val view: T
    }

    interface BaseModel {
        // Nothing to add
    }
}