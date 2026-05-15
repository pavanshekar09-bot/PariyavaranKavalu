package com.example.pariyavarankavalu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay

// ─────────────────────────────────────────
//  THEME / COLORS
// ─────────────────────────────────────────
private val EcoGreen        = Color(0xFF2E7D32)
private val EcoGreenLight   = Color(0xFF4CAF50)
private val EcoGreenBright  = Color(0xFF66BB6A)
private val EcoTeal         = Color(0xFF00897B)
private val BackgroundDark  = Color(0xFF0D1F0E)
private val SurfaceDark     = Color(0xFF142615)
private val CardDark        = Color(0xFF1B3A1C)
private val OnSurface       = Color(0xFFE8F5E9)
private val OnSurfaceMuted  = Color(0xFF9E9E9E)
private val GoldAccent      = Color(0xFFFFC107)
private val RedAccent        = Color(0xFFEF5350)
private val YellowStatus    = Color(0xFFFFC107)
private val GreenStatus     = Color(0xFF4CAF50)
private val BlueStatus      = Color(0xFF42A5F5)

private val EcoGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF1B5E20), Color(0xFF2E7D32), Color(0xFF388E3C))
)
private val ButtonGradient = Brush.linearGradient(
    colors = listOf(EcoGreenLight, EcoTeal)
)

// ─────────────────────────────────────────
//  DATA MODELS
// ─────────────────────────────────────────
data class Report(
    val id: String,
    val wasteType: String,
    val description: String,
    val location: String,
    val timeAgo: String,
    val status: String,
    val ecoPoints: Int = 10
)

data class UserProfile(
    val name: String = "Pavan Shekar",
    val phone: String = "+91 98765 43210",
    val ecoPoints: Int = 340,
    val totalReports: Int = 28,
    val cleanedReports: Int = 19,
    val pendingReports: Int = 9,
    val joinDate: String = "January 2025",
    val level: String = "Eco Warrior"
)

val sampleReports = listOf(
    Report("1", "Plastic Waste",   "Large plastic bags near the road",       "MG Road, Bangalore",       "2 hours ago",  "Reported",     10),
    Report("2", "Organic Waste",   "Food waste near the park entrance",       "Cubbon Park, Bangalore",   "1 day ago",    "In Progress",  10),
    Report("3", "Mixed Waste",     "Dumped garbage pile behind the market",   "KR Market, Bangalore",     "2 days ago",   "Cleaned",      10),
    Report("4", "Construction Waste","Rubble left after construction",         "Koramangala, Bangalore",   "4 days ago",   "Cleaned",      10),
    Report("5", "Plastic Waste",   "Plastic bottles clogging the drain",      "Brigade Road, Bangalore",  "5 days ago",   "Reported",     10),
    Report("6", "Organic Waste",   "Rotting vegetables near the bus stop",    "Majestic, Bangalore",      "1 week ago",   "Cleaned",      10),
)

val wasteTypes = listOf("Plastic Waste", "Organic Waste", "Mixed Waste", "Construction Waste", "Other")

enum class Screen { Splash, Login, Home, ReportWaste, Reports, Profile }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary           = EcoGreenLight,
                    onPrimary         = Color.Black,
                    background        = BackgroundDark,
                    onBackground      = OnSurface,
                    surface           = SurfaceDark,
                    onSurface         = OnSurface,
                    surfaceVariant    = CardDark,
                    onSurfaceVariant  = OnSurface
                )
            ) {
                ParyavaranApp()
            }
        }
    }
}

@Composable
fun ParyavaranApp() {
    var currentScreen by remember { mutableStateOf(Screen.Splash) }
    var user         by remember { mutableStateOf(UserProfile()) }
    var reports      by remember { mutableStateOf(sampleReports) }
    var nextId       by remember { mutableIntStateOf(sampleReports.size + 1) }

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            (fadeIn(tween(300)) + slideInHorizontally { it / 4 })
                .togetherWith(fadeOut(tween(200)) + slideOutHorizontally { -it / 4 })
        },
        label = "nav"
    ) { screen ->
        when (screen) {
            Screen.Splash      -> SplashScreen   { currentScreen = Screen.Login }
            Screen.Login       -> LoginScreen    { currentScreen = Screen.Home  }
            Screen.Home        -> HomeScreen(user, reports,
                onReport  = { currentScreen = Screen.ReportWaste },
                onReports = { currentScreen = Screen.Reports     },
                onProfile = { currentScreen = Screen.Profile     })
            Screen.ReportWaste -> ReportWasteScreen(
                onBack   = { currentScreen = Screen.Home    },
                onSubmit = { wasteType, desc, location ->
                    val newReport = Report(
                        id          = nextId.toString(),
                        wasteType   = wasteType,
                        description = desc,
                        location    = location,
                        timeAgo     = "Just now",
                        status      = "Reported"
                    )
                    reports = listOf(newReport) + reports
                    user    = user.copy(
                        ecoPoints    = user.ecoPoints    + 10,
                        totalReports = user.totalReports + 1,
                        pendingReports = user.pendingReports + 1
                    )
                    nextId++
                    currentScreen = Screen.Reports
                })
            Screen.Reports     -> ReportsScreen(reports,
                onHome    = { currentScreen = Screen.Home    },
                onProfile = { currentScreen = Screen.Profile })
            Screen.Profile     -> ProfileScreen(user,
                onHome    = { currentScreen = Screen.Home    },
                onReports = { currentScreen = Screen.Reports })
        }
    }
}

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) { delay(2500); onFinished() }

    val pulse by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 0.95f, targetValue = 1.05f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "scale"
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val step = 60f
            var x = 0f
            while (x < size.width) {
                drawLine(Color(0x0A4CAF50), Offset(x, 0f), Offset(x, size.height), 1f)
                x += step
            }
            var y = 0f
            while (y < size.height) {
                drawLine(Color(0x0A4CAF50), Offset(0f, y), Offset(size.width, y), 1f)
                y += step
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier
                    .size(110.dp)
                    .scale(pulse)
                    .background(
                        Brush.radialGradient(listOf(EcoGreenLight, EcoGreen)),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("🌿", fontSize = 52.sp)
            }

            Spacer(Modifier.height(28.dp))

            Text(
                "Paryavaran-Kavalu",
                color = EcoGreenBright,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Smart Waste Reporting System",
                color = OnSurfaceMuted,
                fontSize = 14.sp,
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(56.dp))
            CircularProgressIndicator(color = EcoGreenLight, strokeWidth = 2.dp, modifier = Modifier.size(28.dp))
        }

        Text(
            "CleanTheSpot v1.0",
            color = Color(0x55FFFFFF),
            fontSize = 11.sp,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp)
        )
    }
}

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var phone    by remember { mutableStateOf("") }
    var otp      by remember { mutableStateOf("") }
    var otpSent  by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Canvas(Modifier.fillMaxWidth().height(260.dp)) {
            drawOval(
                brush = Brush.radialGradient(
                    listOf(Color(0xFF2E7D32), Color(0xFF1B5E20), Color.Transparent),
                    radius = 700f, center = Offset(size.width / 2, -100f)
                ),
                topLeft = Offset(-100f, -200f),
                size = androidx.compose.ui.geometry.Size(size.width + 200f, 500f)
            )
        }

        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(64.dp))
            Box(
                Modifier.size(80.dp).background(Color(0x33FFFFFF), CircleShape),
                contentAlignment = Alignment.Center
            ) { Text("🌱", fontSize = 38.sp) }

            Spacer(Modifier.height(16.dp))
            Text("Paryavaran-Kavalu", color = OnSurface, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Smart Waste Reporting", color = EcoGreenBright, fontSize = 13.sp)

            Spacer(Modifier.height(40.dp))

            Surface(color = CardDark, shape = RoundedCornerShape(20.dp), tonalElevation = 2.dp) {
                Column(Modifier.padding(24.dp)) {
                    Text("Welcome Back", color = OnSurface, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Text("Sign in to continue", color = OnSurfaceMuted, fontSize = 12.sp)
                    Spacer(Modifier.height(20.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { if (it.length <= 10) phone = it.filter { c -> c.isDigit() } },
                        label = { Text("Phone Number") },
                        leadingIcon = { Text("+91 ", color = EcoGreenBright, fontWeight = FontWeight.Bold) },
                        placeholder = { Text("10-digit number", color = OnSurfaceMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor    = EcoGreenLight,
                            unfocusedBorderColor  = Color(0x44FFFFFF),
                            focusedTextColor      = OnSurface,
                            unfocusedTextColor    = OnSurface,
                            cursorColor           = EcoGreenLight,
                            focusedLabelColor     = EcoGreenLight
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(Modifier.height(12.dp))
                    GradientButton("Send OTP", Modifier.fillMaxWidth()) {
                        if (phone.length == 10) otpSent = true
                    }

                    AnimatedVisibility(otpSent) {
                        Column {
                            Spacer(Modifier.height(16.dp))
                            OutlinedTextField(
                                value = otp,
                                onValueChange = { if (it.length <= 6) otp = it.filter { c -> c.isDigit() } },
                                label = { Text("Enter OTP") },
                                placeholder = { Text("6-digit OTP", color = OnSurfaceMuted) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor    = EcoGreenLight,
                                    unfocusedBorderColor  = Color(0x44FFFFFF),
                                    focusedTextColor      = OnSurface,
                                    unfocusedTextColor    = OnSurface,
                                    cursorColor           = EcoGreenLight,
                                    focusedLabelColor     = EcoGreenLight
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(Modifier.height(12.dp))
                            GradientButton("Verify & Continue", Modifier.fillMaxWidth()) {
                                onLogin()
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        HorizontalDivider(Modifier.weight(1f), color = Color(0x44FFFFFF))
                        Text("  OR  ", color = OnSurfaceMuted, fontSize = 12.sp)
                        HorizontalDivider(Modifier.weight(1f), color = Color(0x44FFFFFF))
                    }
                    Spacer(Modifier.height(14.dp))
                    OutlinedButton(
                        onClick = onLogin,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0x44FFFFFF)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurface)
                    ) {
                        Text("🔵  Continue with Google", modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun HomeScreen(
    user: UserProfile,
    reports: List<Report>,
    onReport: () -> Unit,
    onReports: () -> Unit,
    onProfile: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            HomeTopBar(
                showMenu  = showMenu,
                onMenu    = { showMenu = !showMenu },
                onMenuDismiss = { showMenu = false },
                onProfile = onProfile,
                onReports = onReports
            )
        },
        bottomBar = { BottomNav(current = 0, onHome = {}, onReports = onReports, onProfile = onProfile) }
    ) { padding ->
        LazyColumn(
            Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(EcoGradient)
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    Column {
                        Text("Welcome back,", color = Color(0xBBFFFFFF), fontSize = 13.sp)
                        Text(user.name, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(16.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            StatChip(Modifier.weight(1f), "🏆", "${user.ecoPoints}", "Eco Points", GoldAccent)
                            StatChip(Modifier.weight(1f), "📊", "${user.totalReports}", "Reports",   EcoGreenBright)
                        }
                    }
                }
            }

            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 8.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            Modifier
                                .size(150.dp)
                                .background(
                                    Brush.radialGradient(listOf(Color(0x334CAF50), Color.Transparent)),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                Modifier
                                    .size(120.dp)
                                    .shadow(12.dp, CircleShape)
                                    .background(ButtonGradient, CircleShape)
                                    .clickable(onClick = onReport),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Filled.CameraAlt, null, tint = Color.White, modifier = Modifier.size(34.dp))
                                    Spacer(Modifier.height(4.dp))
                                    Text("REPORT", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                                    Text("WASTE",  color = Color(0xCCFFFFFF), fontSize = 9.sp,  letterSpacing = 1.5.sp)
                                }
                            }
                        }
                        Spacer(Modifier.height(6.dp))
                        Text("Tap to report waste near you", color = OnSurfaceMuted, fontSize = 12.sp)
                    }
                }
            }

            item {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickAction(Modifier.weight(1f), "📋", "My Reports") { onReports() }
                    QuickAction(Modifier.weight(1f), "🏅", "Eco Points") {}
                    QuickAction(Modifier.weight(1f), "🗺️", "Nearby")    {}
                }
            }

            item {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Recent Reports", color = OnSurface, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    TextButton(onClick = onReports) { Text("See all", color = EcoGreenBright, fontSize = 12.sp) }
                }
            }

            items(reports.take(3)) { report ->
                ReportCard(report, Modifier.padding(horizontal = 16.dp, vertical = 6.dp))
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun ReportWasteScreen(
    onBack: () -> Unit,
    onSubmit: (wasteType: String, desc: String, location: String) -> Unit
) {
    var selectedWasteType by remember { mutableStateOf("") }
    var description       by remember { mutableStateOf("") }
    var dropdownExpanded  by remember { mutableStateOf(false) }
    var locationText      by remember { mutableStateOf("Fetching location…") }
    var imageAttached     by remember { mutableStateOf(false) }
    var submitting        by remember { mutableStateOf(false) }
    var errorMsg          by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        delay(1800)
        locationText = "📍 12.9716° N, 77.5946° E\nBangalore, Karnataka, India"
    }

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            Surface(color = SurfaceDark, tonalElevation = 2.dp) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = OnSurface)
                    }
                    Text("Report Waste", color = OnSurface, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(CardDark)
                        .border(
                            BorderStroke(
                                if (imageAttached) 2.dp else 1.dp,
                                if (imageAttached) EcoGreenLight else Color(0x33FFFFFF)
                            ),
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { imageAttached = !imageAttached },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageAttached) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("🖼️", fontSize = 44.sp)
                            Spacer(Modifier.height(8.dp))
                            Text("Image attached ✓", color = EcoGreenBright, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                            Text("Tap to remove", color = OnSurfaceMuted, fontSize = 11.sp)
                        }
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Filled.AddAPhoto, null, tint = OnSurfaceMuted, modifier = Modifier.size(40.dp))
                            Spacer(Modifier.height(8.dp))
                            Text("Tap to add photo", color = OnSurfaceMuted, fontSize = 13.sp)
                        }
                    }
                }
            }

            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = { imageAttached = true },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, EcoGreenLight),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = EcoGreenBright)
                    ) {
                        Icon(Icons.Filled.CameraAlt, null, Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Camera")
                    }
                    OutlinedButton(
                        onClick = { imageAttached = true },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, EcoGreen),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = EcoGreenBright)
                    ) {
                        Icon(Icons.Filled.Image, null, Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Gallery")
                    }
                }
            }

            item {
                FormLabel("Waste Type *")
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = if (selectedWasteType.isEmpty()) "Select waste type" else selectedWasteType,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                if (dropdownExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                null, tint = EcoGreenBright
                            )
                        },
                        colors = outlinedFieldColors(selectedWasteType.isNotEmpty()),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { dropdownExpanded = true }
                    )
                    
                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                        modifier = Modifier.background(CardDark)
                    ) {
                        wasteTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type, color = OnSurface) },
                                onClick = { 
                                    selectedWasteType = type
                                    dropdownExpanded = false 
                                }
                            )
                        }
                    }
                }
            }

            item {
                FormLabel("Description (Optional)")
                OutlinedTextField(
                    value = description,
                    onValueChange = { if (it.length <= 200) description = it },
                    placeholder = { Text("Describe the waste situation…", color = OnSurfaceMuted) },
                    colors = outlinedFieldColors(description.isNotEmpty()),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    maxLines = 4
                )
                Text("${description.length}/200", color = OnSurfaceMuted, fontSize = 11.sp,
                    modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.End).padding(top = 2.dp))
            }

            item {
                FormLabel("Location")
                Surface(
                    color = CardDark,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0x33FFFFFF))
                ) {
                    Row(
                        Modifier.fillMaxWidth().padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.LocationOn, null, tint = EcoGreenLight, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(10.dp))
                        if (locationText.startsWith("Fetching")) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(color = EcoGreenLight, strokeWidth = 2.dp, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("Fetching location…", color = OnSurfaceMuted, fontSize = 13.sp)
                            }
                        } else {
                            Text(locationText, color = OnSurface, fontSize = 13.sp, lineHeight = 20.sp)
                        }
                    }
                }
            }

            if (errorMsg.isNotEmpty()) {
                item {
                    Surface(color = Color(0x22EF5350), shape = RoundedCornerShape(10.dp)) {
                        Text("⚠️ $errorMsg", color = RedAccent, fontSize = 12.sp,
                            modifier = Modifier.padding(12.dp))
                    }
                }
            }

            item {
                Spacer(Modifier.height(4.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .shadow(8.dp, RoundedCornerShape(14.dp))
                        .background(ButtonGradient, RoundedCornerShape(14.dp))
                        .clickable(enabled = !submitting) {
                            when {
                                !imageAttached         -> errorMsg = "Please attach a photo."
                                selectedWasteType.isEmpty() -> errorMsg = "Please select a waste type."
                                locationText.startsWith("Fetching") -> errorMsg = "Please wait for GPS location."
                                else -> {
                                    errorMsg = ""; submitting = true
                                    onSubmit(selectedWasteType, description, "Bangalore, Karnataka")
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (submitting) {
                        CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp, modifier = Modifier.size(24.dp))
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.CheckCircle, null, tint = Color.White, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Submit Report", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun ReportsScreen(
    reports: List<Report>,
    onHome: () -> Unit,
    onProfile: () -> Unit
) {
    var search       by remember { mutableStateOf("") }
    var filter       by remember { mutableStateOf("All") }
    var filterExpand by remember { mutableStateOf(false) }
    val filterOptions = listOf("All", "Reported", "In Progress", "Cleaned")

    val filtered = reports.filter { r ->
        (filter == "All" || r.status == filter) &&
                (search.isEmpty() || r.wasteType.contains(search, ignoreCase = true) || r.location.contains(search, ignoreCase = true))
    }

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            Surface(color = SurfaceDark, tonalElevation = 2.dp) {
                Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                    Text("My Reports", color = OnSurface, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = search,
                            onValueChange = { search = it },
                            placeholder = { Text("Search reports…", color = OnSurfaceMuted) },
                            leadingIcon  = { Icon(Icons.Filled.Search, null, tint = EcoGreenBright) },
                            colors = outlinedFieldColors(search.isNotEmpty()),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        Box {
                            OutlinedButton(
                                onClick = { filterExpand = true },
                                border = BorderStroke(1.dp, EcoGreen),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = EcoGreenBright)
                            ) {
                                Text(filter, fontSize = 12.sp)
                                Icon(Icons.Filled.KeyboardArrowDown, null, Modifier.size(16.dp))
                            }
                            DropdownMenu(expanded = filterExpand, onDismissRequest = { filterExpand = false },
                                modifier = Modifier.background(CardDark)) {
                                filterOptions.forEach { opt ->
                                    DropdownMenuItem(
                                        text = { Text(opt, color = if (filter == opt) EcoGreenBright else OnSurface) },
                                        onClick = { filter = opt; filterExpand = false }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        bottomBar = { BottomNav(current = 1, onHome = onHome, onReports = {}, onProfile = onProfile) }
    ) { padding ->
        if (filtered.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📭", fontSize = 48.sp)
                    Spacer(Modifier.height(12.dp))
                    Text("No reports found", color = OnSurfaceMuted, fontSize = 16.sp)
                }
            }
        } else {
            LazyColumn(Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                item {
                    Text("${filtered.size} report${if (filtered.size != 1) "s" else ""}",
                        color = OnSurfaceMuted, fontSize = 12.sp)
                }
                items(filtered) { report ->
                    ReportCard(report)
                }
                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
fun ProfileScreen(
    user: UserProfile,
    onHome: () -> Unit,
    onReports: () -> Unit
) {
    Scaffold(
        containerColor = BackgroundDark,
        bottomBar = { BottomNav(current = 2, onHome = onHome, onReports = onReports, onProfile = {}) }
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(bottom = 24.dp)) {
            item {
                Box(
                    Modifier.fillMaxWidth().background(EcoGradient).padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            Modifier.size(88.dp)
                                .shadow(6.dp, CircleShape)
                                .background(CardDark, CircleShape)
                                .border(3.dp, EcoGreenLight, CircleShape),
                            contentAlignment = Alignment.Center
                        ) { Text("👤", fontSize = 40.sp) }
                        Spacer(Modifier.height(12.dp))
                        Text(user.name, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(user.phone, color = Color(0xBBFFFFFF), fontSize = 13.sp)
                        Spacer(Modifier.height(8.dp))
                        Surface(color = Color(0x33FFFFFF), shape = RoundedCornerShape(20.dp)) {
                            Text("🌿  ${user.level}", color = EcoGreenBright, fontSize = 12.sp,
                                fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
                        }
                        Spacer(Modifier.height(4.dp))
                        Text("Member since ${user.joinDate}", color = Color(0x88FFFFFF), fontSize = 11.sp)
                    }
                }
            }

            item {
                Spacer(Modifier.height(16.dp))
                Surface(
                    color = CardDark,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                ) {
                    Row(Modifier.padding(20.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                        ProfileStat("🏆", "${user.ecoPoints}", "Eco Points", GoldAccent)
                        VerticalDivider()
                        ProfileStat("📊", "${user.totalReports}", "Reports",    EcoGreenBright)
                        VerticalDivider()
                        ProfileStat("✅", "${user.cleanedReports}", "Cleaned",  GreenStatus)
                    }
                }
            }

            item {
                Spacer(Modifier.height(16.dp))
                Surface(color = CardDark, shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()) {
                    Column(Modifier.padding(20.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Level Progress", color = OnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            Text("${user.ecoPoints} / 500 pts", color = EcoGreenBright, fontSize = 12.sp)
                        }
                        Spacer(Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { (user.ecoPoints / 500f).coerceIn(0f, 1f) },
                            color = EcoGreenLight,
                            trackColor = Color(0x33FFFFFF),
                            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp))
                        )
                        Spacer(Modifier.height(6.dp))
                        Text("${(500 - user.ecoPoints).coerceAtLeast(0)} points to next level",
                            color = OnSurfaceMuted, fontSize = 11.sp)
                    }
                }
            }

            item { Spacer(Modifier.height(16.dp)) }
            val actions = listOf(
                Triple(Icons.Filled.Notifications,   "Notifications",   "Manage alerts"),
                Triple(Icons.Filled.Language,        "Language",        "English"),
                Triple(Icons.Filled.PrivacyTip,      "Privacy Policy",  "View policy"),
                Triple(Icons.Filled.Info,            "About App",       "Version 1.0"),
                Triple(Icons.AutoMirrored.Filled.Logout,          "Logout",          "Sign out safely")
            )
            items(actions) { (icon, title, sub) ->
                Surface(
                    color = CardDark,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp).fillMaxWidth()
                ) {
                    Row(
                        Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(icon, null, tint = EcoGreenBright, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(14.dp))
                        Column(Modifier.weight(1f)) {
                            Text(title, color = OnSurface, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                            Text(sub, color = OnSurfaceMuted, fontSize = 12.sp)
                        }
                        Icon(Icons.Filled.ChevronRight, null, tint = OnSurfaceMuted, modifier = Modifier.size(18.dp))
                    }
                }
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun HomeTopBar(
    showMenu: Boolean,
    onMenu: () -> Unit,
    onMenuDismiss: () -> Unit,
    onProfile: () -> Unit,
    onReports: () -> Unit
) {
    Surface(color = SurfaceDark, tonalElevation = 2.dp) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                IconButton(onClick = onMenu) {
                    Icon(Icons.Filled.Menu, null, tint = OnSurface)
                }
                DropdownMenu(expanded = showMenu, onDismissRequest = onMenuDismiss,
                    modifier = Modifier.background(CardDark)) {
                    listOf("🌿  About App", "📋  My Reports", "🏆  Eco Points", "⚙️  Settings", "🚪  Logout")
                        .forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item, color = OnSurface) },
                                onClick = {
                                    onMenuDismiss()
                                    if (item.contains("Reports")) onReports()
                                }
                            )
                        }
                }
            }
            Text(
                "Paryavaran-Kavalu",
                color = EcoGreenBright,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = onProfile) {
                Box(
                    Modifier.size(34.dp)
                        .background(EcoGreen, CircleShape)
                        .border(1.5.dp, EcoGreenLight, CircleShape),
                    contentAlignment = Alignment.Center
                ) { Text("👤", fontSize = 15.sp) }
            }
        }
    }
}

@Composable
fun BottomNav(current: Int, onHome: () -> Unit, onReports: () -> Unit, onProfile: () -> Unit) {
    Surface(color = SurfaceDark, tonalElevation = 6.dp) {
        Row(
            Modifier.fillMaxWidth().navigationBarsPadding().padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(
                Triple(Icons.Filled.Home,       "Home",    0),
                Triple(Icons.AutoMirrored.Filled.List,        "Reports", 1),
                Triple(Icons.Filled.Person,      "Profile", 2)
            ).forEach { (icon, label, idx) ->
                val active = idx == current
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = when (idx) { 0 -> onHome; 1 -> onReports; else -> onProfile })
                        .padding(vertical = 8.dp)
                ) {
                    if (active) {
                        Box(
                            Modifier.size(40.dp).background(EcoGreen.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) { Icon(icon, null, tint = EcoGreenBright, modifier = Modifier.size(22.dp)) }
                    } else {
                        Icon(icon, null, tint = OnSurfaceMuted, modifier = Modifier.size(22.dp))
                    }
                    Spacer(Modifier.height(2.dp))
                    Text(label, color = if (active) EcoGreenBright else OnSurfaceMuted,
                        fontSize = 11.sp, fontWeight = if (active) FontWeight.SemiBold else FontWeight.Normal)
                }
            }
        }
    }
}

@Composable
fun ReportCard(report: Report, modifier: Modifier = Modifier) {
    val (statusColor, statusIcon) = when (report.status) {
        "Reported"    -> YellowStatus to "🟡"
        "In Progress" -> BlueStatus   to "🔵"
        else           -> GreenStatus  to "🟢"
    }

    Surface(
        color = CardDark,
        shape = RoundedCornerShape(14.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(64.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Brush.linearGradient(listOf(EcoGreen, Color(0xFF1B5E20)))),
                contentAlignment = Alignment.Center
            ) { Text("📸", fontSize = 26.sp) }

            Spacer(Modifier.width(14.dp))

            Column(Modifier.weight(1f)) {
                Text(report.wasteType, color = OnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.LocationOn, null, tint = EcoGreenBright, modifier = Modifier.size(12.dp))
                    Spacer(Modifier.width(3.dp))
                    Text(report.location, color = OnSurfaceMuted, fontSize = 11.sp,
                        maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                Spacer(Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Schedule, null, tint = OnSurfaceMuted, modifier = Modifier.size(11.dp))
                    Spacer(Modifier.width(3.dp))
                    Text(report.timeAgo, color = OnSurfaceMuted, fontSize = 11.sp)
                }
                Spacer(Modifier.height(6.dp))
                Surface(
                    color = statusColor.copy(alpha = 0.18f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("$statusIcon ${report.status}", color = statusColor, fontSize = 11.sp,
                        fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                }
            }
        }
    }
}

@Composable
fun StatChip(modifier: Modifier, emoji: String, value: String, label: String, valueColor: Color) {
    Surface(color = Color(0x33000000), shape = RoundedCornerShape(12.dp), modifier = modifier) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(emoji, fontSize = 20.sp)
            Spacer(Modifier.width(8.dp))
            Column {
                Text(value, color = valueColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(label, color = Color(0xAAFFFFFF), fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun QuickAction(modifier: Modifier, emoji: String, label: String, onClick: () -> Unit) {
    Surface(color = CardDark, shape = RoundedCornerShape(14.dp), modifier = modifier) {
        Column(
            Modifier.clickable(onClick = onClick).padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, fontSize = 22.sp)
            Spacer(Modifier.height(4.dp))
            Text(label, color = OnSurface, fontSize = 11.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun ProfileStat(emoji: String, value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(emoji, fontSize = 20.sp)
        Spacer(Modifier.height(4.dp))
        Text(value, color = color, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(label, color = OnSurfaceMuted, fontSize = 11.sp)
    }
}

@Composable
fun VerticalDivider() {
    Box(Modifier.width(1.dp).height(48.dp).background(Color(0x33FFFFFF)))
}

@Composable
fun FormLabel(text: String) {
    Text(
        text, color = OnSurfaceMuted, fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
fun GradientButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier
            .height(50.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(ButtonGradient, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun outlinedFieldColors(active: Boolean) = OutlinedTextFieldDefaults.colors(
    focusedBorderColor   = EcoGreenLight,
    unfocusedBorderColor = if (active) EcoGreen else Color(0x44FFFFFF),
    focusedTextColor     = OnSurface,
    unfocusedTextColor   = OnSurface,
    cursorColor          = EcoGreenLight,
    focusedLabelColor    = EcoGreenLight
)
