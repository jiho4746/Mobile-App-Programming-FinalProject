package com.example.finalproject

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View

import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.finalproject.databinding.ActivityMainBinding
import com.example.myapplication20.util.myCheckPermission

import com.github.mikephil.charting.charts.LineChart

import com.github.mikephil.charting.data.ChartData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences

    private val TAG = this.javaClass.simpleName
    lateinit var lineChart: LineChart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // sharedPreferences 사용

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val bgColor = sharedPreferences.getString("color", "")
        binding.rootLayout.setBackgroundColor(Color.parseColor(bgColor))



        //차트
        val lineFragment = LineFragment()
        val raderFragment = RaderFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, lineFragment)
            .commit()

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.tab1 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, lineFragment)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab2 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, raderFragment)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }

       //주소록 가져오기
        val requestContractLauncher : ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                if(it.resultCode == RESULT_OK){
                    Log.d("mobileApp", "${it.data?.data}")
                    val cursor = contentResolver.query(
                        it!!.data!!.data!!,
                        arrayOf<String>(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER),
                        null, null, null
                    )
                    if(cursor!!.moveToFirst()){
                        val name = cursor?.getString(0)
                        val phone = cursor?.getString(1)
                        binding.addrTv1.text = "$name"
                        binding.addrTv2.text = "$phone"

                    }
                }
            }
        binding.addrBtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            requestContractLauncher.launch(intent)
        }
        // 주소록 가져오기 (끝)

        // 문자 전송
        binding.smsBtn.setOnClickListener{
            if(binding.addrTv2.text.toString() != null){
                val inputPhoneNum = binding.addrTv2.text.toString()
                val myUri = Uri.parse("smsto:${inputPhoneNum}")
                val myIntent = Intent(Intent.ACTION_SENDTO, myUri)

                // 문자 전송 화면 이동 시 미리 문구를 적어서 보내기
                // myIntent를 가지고 갈 때 -> putExtra로 데이터를 담아서 보내자
                myIntent.putExtra("sms_body", "위험한 상황에 처해있어. 도와줘")
                startActivity(myIntent)
            }
            //else{
             //   binding.addrTv1.text = "주소록에서 선택된 연락처가 없어요"
             //   binding.addrTv2.text = "추가해주세요"
            //}
        }
        // 문자 전송 (끝)

        // 전화 & 지도 : 사용자에게 권한 허용을 물어봄
        // 지도앱 (검색 가능)
        binding.mapBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo: 37.562952, 126.9779451"))
            startActivity(intent)
        }

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
        }

        binding.callBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:02-112"))
            val status = ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE")
            if(status== PackageManager.PERMISSION_GRANTED){
                startActivity(intent)
            }
            else{
                requestPermissionLauncher.launch("android.permission.CALL_PHONE")
            }
        }
        // 전화 & 지도 (끝)

        //var keyHash = Utility.getKeyHash(this)
        //Log.d("keyHash", keyHash)

        val xmlfragment = XmlFragment()
        val bundle = Bundle()

        binding.searchBtn.setOnClickListener {
            bundle.putString("returnType", "json")
            xmlfragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_content, xmlfragment)
                .commit()
            }

        // 사이트 이동
        binding.webSite.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://onetouch.police.go.kr"))
            startActivity(intent)
            //val intent = Intent(this, WebActivity::class.java)
            //startActivity(intent)
        }

        myCheckPermission(this)
        binding.addFab.setOnClickListener {
            if(MyApplication.checkAuth()){
                startActivity(Intent(this, AddActivity::class.java))
            }
            else{
                Toast.makeText(this, "인증 진행해주세요요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            if(binding.btnLogin.text.equals("로그인"))
                intent.putExtra("data", "logout")
            else if(binding.btnLogin.text.equals("로그아웃"))
                intent.putExtra("data", "login")

            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if(MyApplication.checkAuth() || MyApplication.email != null){
            binding.btnLogin.text="로그아웃"
            binding.authTv.text="One Touch"
            binding.addrTv3.text="${MyApplication.email}님 반갑습니다."
            binding.authTv.textSize=16F
            //RecyclerView 보이기
            binding.mainRecyclerView.visibility = View.VISIBLE
            makeRecyclerView()
        }
        else{
            binding.btnLogin.text="로그인"
            binding.authTv.text="One Touch"
            binding.authTv.textSize=24F
            //binding.mainRecyclerView.visibility = View.GONE
        }

    }
    private fun makeRecyclerView(){
        MyApplication.db.collection("news")
            .get()
            .addOnSuccessListener { result->
                val itemList = mutableListOf<ItemData>()
                for(document in result){
                    val item = document.toObject(ItemData::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }
                binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.mainRecyclerView.adapter = MyAdapter(this, itemList)
            }
            .addOnFailureListener {
                Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_SHORT).show()
            }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_main_setting){
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


    // sharedPreference 색상 설정
    override fun onResume() {
        super.onResume()
        val bgColor = sharedPreferences.getString("color", "")
        binding.rootLayout.setBackgroundColor(Color.parseColor(bgColor))
    }


}