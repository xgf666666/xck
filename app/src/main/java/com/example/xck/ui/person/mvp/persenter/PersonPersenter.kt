package com.example.xck.ui.person.mvp.persenter

import com.example.xck.ui.person.mvp.contract.PersonContract
import com.example.xck.ui.person.mvp.model.PersonModel

class PersonPersenter(view: PersonContract.View):PersonContract.Persenter(view) {
    override fun createModel(): PersonContract.Model =PersonModel()
}