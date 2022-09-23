package com.tinderbusiness.activities.tabs

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build.ID
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pixplicity.easyprefs.library.Prefs
import com.tinderbusiness.R
import com.tinderbusiness.activities.BaseActivity
import com.tinderbusiness.activities.MenuActivity
import com.tinderbusiness.adapter.CardStackAdapter
import com.tinderbusiness.databinding.ActivityHomeBinding
import com.tinderbusiness.listener.ContinentListener
import com.tinderbusiness.models.BusinessMatch
import com.tinderbusiness.models.UserModel
import com.tinderbusiness.utils.Constants
import com.tinderbusiness.utils.Constants.g_user
import com.tinderbusiness.utils.ContinentDialog
import com.yuyakaido.android.cardstackview.*
import com.yuyakaido.android.cardstackview.sample.SpotDiffCallback


class HomeActivity : BaseActivity(), View.OnClickListener, CardStackListener, ContinentListener {
    private lateinit var binding : ActivityHomeBinding

    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val adapter by lazy { CardStackAdapter(createMatch(), this) }
    var continentDialog : ContinentDialog? = ContinentDialog()
    val db = Firebase.firestore
    var userData : ArrayList<UserModel> =  ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_home)

        loadLayout()
        setupCardStackView()
    }

    private fun loadLayout(){

        binding.header.imvMenu.setOnClickListener(this)
        binding.header.imvInter.setOnClickListener(this)

        binding.rewindButton.setOnClickListener(this)
        binding.skipButton.setOnClickListener(this)
        binding.superLikeButton.setOnClickListener(this)
        binding.likeButton.setOnClickListener(this)
        binding.lightningButton.setOnClickListener(this)

        binding.tab.imvHome.setOnClickListener(this)
        binding.tab.imvSetting.setOnClickListener(this)
        binding.tab.imvBoost.setOnClickListener(this)
        binding.tab.imvChat.setOnClickListener(this)
        continentDialog?.continentListener = this

        if (Prefs.getBoolean(Constants.FIRST_TIME, true))
            chatNowDialog()

        getAllData(Prefs.getString(Constants.CONTINENT, ""))
    }

    private fun chatNowDialog(){

        Prefs.putBoolean(Constants.FIRST_TIME, false)

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_chat_now)
        dialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(this, R.color.transparent))
        )

        //dialog.setCancelable(true)
        //dialog.setCanceledOnTouchOutside(true)

        val imvClose = dialog.findViewById<ImageView>(R.id.imvClose)
        val txvName : TextView = dialog.findViewById(R.id.txvName)
        val btnChatNow = dialog.findViewById<Button>(R.id.btnChatNow)

        imvClose.setOnClickListener{
            dialog.dismiss()
        }
        btnChatNow.setOnClickListener{

            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(Constants.CHAT_NAME, "BOOM")
            startActivity(intent)
            dialog.dismiss()
        }
        dialog.show()
    }
    override fun onClick(p0: View?) {
        when(p0?.id){

            R.id.imvMenu ->{
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            }
            R.id.imvInter ->{
                continentDialog?.internationalDialog(this)
            }
            R.id.imvHome ->{

            }
            R.id.imvSetting ->{
                startActivity(Intent(this, SettingActivity::class.java))
                finish()
            }

            R.id.imvBoost ->{
                startActivity(Intent(this, BoostActivity::class.java))
            }

            R.id.imvChat ->{
                startActivity(Intent(this, ChatListActivity::class.java))
                finish()
            }
            R.id.rewind_button ->{

                val setting = RewindAnimationSetting.Builder()
                    .setDirection(Direction.Bottom)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(DecelerateInterpolator())
                    .build()
                manager.setRewindAnimationSetting(setting)
                binding.cardStackView.rewind()
            }

            R.id.skip_button ->{

                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                manager.setSwipeAnimationSetting(setting)
                binding.cardStackView.swipe()
            }

            R.id.super_like_button ->{

            }

            R.id.like_button ->{

                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                manager.setSwipeAnimationSetting(setting)
                binding.cardStackView.swipe()
            }

            R.id.lightning_button ->{

            }
        }
    }

    private fun setupCardStackView() {
        initialize()
    }

    private fun initialize() {

        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        //manager.setDirections(Direction.HORIZONTAL)
        /*manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(false)*/
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = adapter
        binding.cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun getAllData(strContinent : String){

        val refDoc: Task<QuerySnapshot>

        if (strContinent.equals(getString(R.string.international)))
            refDoc = db.collection(Constants.USERS).get()
        else if (strContinent.equals(getString(R.string.local))) {
            refDoc = db.collection(Constants.USERS).whereEqualTo(Constants.COUNTRY, g_user.country).get()
        }
        else{
            refDoc = db.collection(Constants.USERS).whereEqualTo(Constants.CONTINENT, strContinent).get()
        }

        showLoader()

        refDoc
            .addOnSuccessListener { result ->
                for (document in result){

                    if (g_user.id == document.id)
                        continue

                    Log.d("TAG", "${document.id} => ${document.data}")

                    val user = UserModel()

                    user.id = document.get(Constants.ID).toString()
                    user.contactName = document.get(Constants.CONTACT_NAME).toString()
                    user.email = document.get(Constants.EMAIL).toString()
                    user.country = document.get(Constants.COUNTRY).toString()
                    user.continent = document.get(Constants.CONTINENT).toString()
                    user.industry = document.get(Constants.INDUSTRY).toString()
                    user.interestedIndustry = document.get(Constants.INTERESTED_INDUSTRY).toString()
                    user.typesCollaborations = document.get(Constants.COLLABORATIONS).toString()
                    user.description = document.get(Constants.DESCRIPTION).toString()
                    user.budget = document.get(Constants.BUDGET).toString()

                    userData.add(user)
                }

                closeLoader()

                runOnUiThread{

                }
            }
            .addOnFailureListener{ exception ->
                Log.d("Home=>", exception.message.toString())
            }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        if (manager.topPosition == adapter.itemCount - 5) {
            paginate()
        }
    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {
        val textView = view?.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardAppeared: ($position) ${textView?.text}")
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        val textView = view?.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardDisappeared: ($position) ${textView?.text}")
    }

    private fun paginate() {
        val old = adapter.getSpots()
        val new = old.plus(createMatch())
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun reload() {
        val old = adapter.getSpots()
        val new = createMatch()
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun addFirst(size: Int) {
        val old = adapter.getSpots()
        val new = mutableListOf<BusinessMatch>().apply {
            addAll(old)
            for (i in 0 until size) {
                add(manager.topPosition, createBusinessMatch())
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun addLast(size: Int) {
        val old = adapter.getSpots()
        val new = mutableListOf<BusinessMatch>().apply {
            addAll(old)
            addAll(List(size) { createBusinessMatch() })
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun removeFirst(size: Int) {
        if (adapter.getSpots().isEmpty()) {
            return
        }

        val old = adapter.getSpots()
        val new = mutableListOf<BusinessMatch>().apply {
            addAll(old)
            for (i in 0 until size) {
                removeAt(manager.topPosition)
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun removeLast(size: Int) {
        if (adapter.getSpots().isEmpty()) {
            return
        }

        val old = adapter.getSpots()
        val new = mutableListOf<BusinessMatch>().apply {
            addAll(old)
            for (i in 0 until size) {
                removeAt(this.size - 1)
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun replace() {
        val old = adapter.getSpots()
        val new = mutableListOf<BusinessMatch>().apply {
            addAll(old)
            removeAt(manager.topPosition)
            add(manager.topPosition, createBusinessMatch())
        }
        adapter.setSpots(new)
        adapter.notifyItemChanged(manager.topPosition)
    }

    private fun swap() {
        val old = adapter.getSpots()
        val new = mutableListOf<BusinessMatch>().apply {
            addAll(old)
            val first = removeAt(manager.topPosition)
            val last = removeAt(this.size - 1)
            add(manager.topPosition, last)
            add(first)
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)

    }

    override fun onContinentChanged( continent : String){
        getAllData(continent)
    }

    private fun createBusinessMatch(): BusinessMatch {
        return BusinessMatch(
            name = "MICHAEL JACKSON",
            industry = "MANUFACTURING",
            country = "MALAYSIA",
            budget = "30,0000",
            type_collaborations = "Loyalty Program",
            interested_industry = "Biomedical and Engineering Fluid Mechanics",
            description = "Restrict to 30 words preview"
        )
    }

    private fun  createMatch(): List<BusinessMatch> {
        val match = ArrayList<BusinessMatch>()
        match.add(BusinessMatch(name = "Pansy Kelley", industry = "Accommodation Food Services", country = "Singapore", budget = "85,000", type_collaborations = "Loyalty Program", interested_industry = "Finance and Insurance", description = "This is description, Restrict to 150 words preview, This is description, Restrict to 150 words preview, This is description, Restrict to 150 words preview"))
        match.add(BusinessMatch(name = "Angela Nichols", industry = "Agriculture, Forestry, Fishing and Hunting", country = "Malaysia", budget = "15,000", type_collaborations = "Loyalty Program", interested_industry = "Finance and Insurance", description = "Restrict to 10 words preview"))
        match.add(BusinessMatch(name = "Bamboo Forest", industry = "Accommodation Food Services", country = "Thailand", budget = "80,000", type_collaborations = "Loyalty Program", interested_industry = "Finance and Insurance", description = "This is description, Restrict to 30 words preview"))
        match.add(BusinessMatch(name = "Belinda Pascall", industry = "Accommodation Food Services", country = "China", budget = "25,000", type_collaborations = "Loyalty Program", interested_industry = "Finance and Insurance", description = "Restrict to 150 words preview"))
        match.add(BusinessMatch(name = "Paisley Dinwiddie", industry = "Finance and Insurance", country = "Japan", budget = "45000", type_collaborations = "Loyalty Program", interested_industry = "Finance and Insurance", description = "Description2"))
        match.add(BusinessMatch(name = "Theo Schuman", industry = "Agriculture, Forestry, Fishing and Hunting", country = "Korea", budget = "55000", type_collaborations = "Loyalty Program", interested_industry = "Finance and Insurance", description = "Restrict to 100 words preview"))
        match.add(BusinessMatch(name = "Louvre Museum", industry = "Accommodation Food Services", country = "Vietnam", budget = "25,000", type_collaborations = "Loyalty Program", interested_industry = "Finance and Insurance", description = "Description4, Restrict to 30 words preview"))
        match.add(BusinessMatch(name = "Eiffel Tower", industry = "Finance and Insurance", country = "Laos", budget = "30,000", type_collaborations = "Loyalty Program", interested_industry = "Finance and Insurance", description = "Description5, Restrict to 50 words preview"))
        match.add(BusinessMatch(name = "Big Ben", industry = "Finance and Insurance", country = "India", budget = "60,000", type_collaborations = "Loyalty Program", interested_industry = "Finance and Insurance", description = "Description7, Restrict to 300 words preview"))
        match.add(BusinessMatch(name = "Great Wall of China", industry = "Healthcare and Social Assistance", country = "Pakistan", budget = "45,000", type_collaborations = "Loyalty Program", interested_industry = "Finance and Insurance", description = "Description6, Restrict to 3000 words preview"))
        return match
    }

    override fun onBackPressed() {
        onExit()
    }
}