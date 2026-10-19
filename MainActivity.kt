package com.zainab.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Green = Color(0xFF168A58)
private val PaleGreen = Color(0xFFE8F5EE)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ZainabApp() }
    }
}

@Composable
fun ZainabApp() {
    var selectedTab by remember { mutableStateOf("الرئيسية") }
    var selectedCountry by remember { mutableStateOf("") }
    var showNotice by remember { mutableStateOf(false) }
    val countries = listOf("🇺🇸  الولايات المتحدة", "🇨🇦  كندا", "🇬🇧  المملكة المتحدة", "🇫🇷  فرنسا", "🇩🇪  ألمانيا")

    MaterialTheme(colorScheme = lightColorScheme(primary = Green, secondary = Color(0xFF3A9D72), background = Color(0xFFF7FAF8), surface = Color.White)) {
        Scaffold(
            containerColor = Color(0xFFF7FAF8),
            topBar = {
                Column(Modifier.fillMaxWidth().background(Green).padding(horizontal = 20.dp, vertical = 18.dp)) {
                    Text("Zainab", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Text("الأرقام الافتراضية", color = Color.White, fontSize = 14.sp)
                }
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    val tabs = listOf(
                        Triple("الرئيسية", Icons.Default.Smartphone, "الرئيسية"),
                        Triple("الدول", Icons.Default.Public, "الدول"),
                        Triple("المكالمات", Icons.Default.Call, "المكالمات"),
                        Triple("الرسائل", Icons.Default.Chat, "الرسائل"),
                        Triple("الإعدادات", Icons.Default.Settings, "الإعدادات")
                    )
                    tabs.forEach { (label, icon, value) ->
                        NavigationBarItem(selected = selectedTab == value, onClick = { selectedTab = value },
                            icon = { Icon(icon, contentDescription = label) }, label = { Text(label, fontSize = 10.sp) })
                    }
                }
            }
        ) { padding ->
            Column(Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
                when (selectedTab) {
                    "الرئيسية" -> {
                        Card(colors = CardDefaults.cardColors(containerColor = PaleGreen), shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(20.dp)) {
                                Text("مرحبًا بك في Zainab", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Green)
                                Spacer(Modifier.height(8.dp))
                                Text("إدارة الأرقام الافتراضية من مكان واحد.", fontSize = 15.sp)
                                Spacer(Modifier.height(16.dp))
                                Button(onClick = { selectedTab = "الدول" }, modifier = Modifier.fillMaxWidth()) { Text("استعراض الدول") }
                            }
                        }
                        Spacer(Modifier.height(18.dp))
                        Text("الخدمات", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(10.dp))
                        ServiceCard("الأرقام الافتراضية", "استعرض الدول ومعلومات توفر الخدمة", Icons.Default.Public) { selectedTab = "الدول" }
                        ServiceCard("المكالمات", "واجهة أولية لسجل المكالمات", Icons.Default.Call) { selectedTab = "المكالمات" }
                        ServiceCard("الرسائل", "واجهة أولية لصندوق الرسائل", Icons.Default.Chat) { selectedTab = "الرسائل" }
                    }
                    "الدول" -> {
                        Text("اختر الدولة", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text("قائمة تجريبية؛ لا تعني أن رقمًا مجانيًا متاح في هذه الدول.")
                        Spacer(Modifier.height(12.dp))
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(countries) { country ->
                                Card(modifier = Modifier.fillMaxWidth().clickable { selectedCountry = country; showNotice = true }, colors = CardDefaults.cardColors(containerColor = Color.White)) {
                                    Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Text(country, modifier = Modifier.weight(1f), fontSize = 17.sp)
                                        Text("اختيار", color = Green, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                    "المكالمات" -> EmptyService("المكالمات", "تحتاج المكالمات الحقيقية إلى مزود اتصالات يدعم المكالمات الواردة والصادرة.")
                    "الرسائل" -> EmptyService("الرسائل", "تحتاج رسائل SMS الحقيقية إلى رقم ومزود يدعم الإرسال والاستقبال.")
                    "الإعدادات" -> {
                        Text("الإعدادات", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(12.dp))
                        Text("اللغة: العربية")
                        Text("الإصدار: 1.0 — واجهة أولية")
                        Spacer(Modifier.height(12.dp))
                        Text("لا توجد أرقام أو مكالمات حقيقية مفعّلة في هذه النسخة.", color = Color.Gray)
                    }
                }
            }
            if (showNotice) AlertDialog(onDismissRequest = { showNotice = false },
                title = { Text("الدولة المختارة") },
                text = { Text("$selectedCountry\nهذه واجهة تجريبية. الحصول على رقم حقيقي يتطلب مزود خدمة يدعم الدولة والميزة المطلوبة.") },
                confirmButton = { TextButton(onClick = { showNotice = false }) { Text("حسنًا") } })
        }
    }
}

@Composable
private fun ServiceCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).clickable(onClick = onClick), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = Green, modifier = Modifier.size(32.dp))
            Spacer(Modifier.width(14.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(subtitle, color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun EmptyService(title: String, description: String) {
    Column(Modifier.fillMaxWidth().padding(top = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(18.dp))
        Card(colors = CardDefaults.cardColors(containerColor = PaleGreen)) {
            Text(description, modifier = Modifier.padding(20.dp), fontSize = 15.sp)
        }
    }
}
