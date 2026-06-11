package com.davidups.web

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import david_ups_web.composeapp.generated.resources.Res
import david_ups_web.composeapp.generated.resources.profile
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// ── Palette ──────────────────────────────────────────────────────────────────
private val Purple = Color(0xFF6C63FF)
private val Cyan = Color(0xFF00E5FF)
private val Pink = Color(0xFFFF6EC7)
private val DarkBg = Color(0xFF0A0A14)
private val DarkCard = Color(0xFF14142B)
private val TextPrimary = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFFB0B3C1)
private val LightPurple = Color(0xFFA8A3FF)

private val AccentGradient = listOf(Purple, Cyan, Pink, Purple)

// ── Content (mirrors davidups.github.io + real GitHub stats) ─────────────────
private data class Language(val name: String, val label: String, val fill: Float)

private val languages = listOf(
    Language("Kotlin",     "31.8%", 0.318f),
    Language("JavaScript", "29.0%", 0.290f),
    Language("Java",       "14.4%", 0.144f),
    Language("HTML",        "9.9%", 0.099f),
    Language("C#",          "5.7%", 0.057f),
    Language("C++",         "2.7%", 0.027f),
    Language("CSS",         "1.9%", 0.019f),
    Language("C",           "1.3%", 0.013f),
    Language("Dart",        "1.3%", 0.013f),
    Language("Ruby",        "1.0%", 0.010f),
)

private data class Job(val company: String, val role: String, val period: String, val url: String)

private val jobs = listOf(
    Job("TalentoMOBILE", "Android Developer", "November 2019 – Present", "https://www.talentomobile.com/"),
    Job("Yudonpay (Startup)", "Android Developer", "May 2019 – November 2019", "https://yudonpay.com/es/"),
    Job("Unisys", "Android Developer", "December 2017 – May 2019", "https://www.unisys.es/"),
    Job("Elecno S.A.", "Technician", "September 2015 – June 2016", "http://www.elecno.com/"),
)

private data class Project(
    val name: String,
    val tech: String,
    val highlight: String,
    val link: String? = null,
)

private val projects = listOf(
    Project(
        name = "Avantmoney / Avantcard",
        tech = "Kotlin, MVVM, Flow, Motion-Layout, UnitTest",
        highlight = "Banking app for Avantcard – Bankinter",
        link = "https://play.google.com/store/apps/details?id=ie.avantmoney.mobileapp&hl=es&gl=US",
    ),
    Project(
        name = "Chaleco Digital",
        tech = "Kotlin, Flow, Navigation, Material Components",
        highlight = "Altruist app for press freedom – open source",
        link = "https://play.google.com/store/apps/details?id=com.rsf.chalecodigital&hl=en_US&gl=US",
    ),
    Project(
        name = "Zero Barreras",
        tech = "Kotlin, Coroutines, Unit Tests, Fastlane",
        highlight = "Unit tested + Fastlane CI automation",
        link = "https://play.google.com/store/apps/details?id=org.fundacionmdp.zerobarreras&hl=en_US&gl=US",
    ),
    Project(
        name = "EY 7 Trip",
        tech = "Java, Retrofit, RxJava, Dagger",
        highlight = "Reactive stack with RxJava + Dagger",
        link = "https://play.google.com/store/apps/details?id=com.ey.trip7p&hl=en_US&gl=US",
    ),
    Project(
        name = "DKV Insurance",
        tech = "Kotlin, MVVM, Clean Architecture, Koin",
        highlight = "Clean Architecture at scale",
        link = "https://play.google.com/store/apps/details?id=com.dkvservicios.quierocuidarmemas&hl=en_US",
    ),
    Project(
        name = "Repsol & Disa",
        tech = "Kotlin, Retrofit, MVVM, WorkManager",
        highlight = "Background sync with WorkManager",
    ),
    Project(
        name = "Loteria Navidad",
        tech = "Kotlin, Retrofit, Firebase MLKit",
        highlight = "+5,000 downloads on Google Play",
        link = "https://play.google.com/store/apps/details?id=com.davidups.loterianavidad",
    ),
    Project(
        name = "Yudonpay App",
        tech = "Java, MVP, Retrofit",
        highlight = "Startup app built from scratch",
        link = "https://play.google.com/store/apps/details?id=com.smartdreams.yudonpay&hl=es&gl=US",
    ),
)

// ── Root ─────────────────────────────────────────────────────────────────────
@Composable
fun App() {
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = DarkBg,
            surface = DarkCard,
            primary = Purple,
            onBackground = TextPrimary,
            onSurface = TextPrimary,
        )
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize().background(DarkBg)) {
            val compact = maxWidth < 840.dp
            CosmicBackground()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HeroSection()
                MainColumns(compact)
                Footer()
            }
            RadioWidget(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp),
            )
        }
    }
}

// ── Animated background: orbs + stars + meteors ──────────────────────────────
private class Star(val x: Float, val y: Float, val radius: Float, val phase: Float, val speed: Float)

private class Meteor(val x: Float, val length: Float, val speed: Float, val phase: Float, val tilt: Float)

@Composable
private fun CosmicBackground() {
    val stars = remember {
        List(90) {
            Star(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = Random.nextFloat() * 1.6f + 0.6f,
                phase = Random.nextFloat() * 2f * PI.toFloat(),
                speed = Random.nextFloat() * 1.5f + 0.5f,
            )
        }
    }
    val meteors = remember {
        List(28) {
            Meteor(
                x = Random.nextFloat(),
                length = Random.nextFloat() * 0.06f + 0.03f,
                speed = Random.nextFloat() * 5f + 3f,
                phase = Random.nextFloat(),
                tilt = Random.nextFloat() * 0.015f + 0.005f,
            )
        }
    }
    val transition = rememberInfiniteTransition()
    val time by transition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(tween(24_000, easing = LinearEasing)),
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        fun orb(color: Color, cx: Float, cy: Float, r: Float, speed: Float, phase: Float) {
            val ox = cx + cos(time * speed + phase) * w * 0.08f
            val oy = cy + sin(time * speed + phase) * h * 0.06f
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(color.copy(alpha = 0.22f), Color.Transparent),
                    center = Offset(ox, oy),
                    radius = r,
                ),
                radius = r,
                center = Offset(ox, oy),
            )
        }
        orb(Purple, w * 0.2f, h * 0.25f, w * 0.35f, 1f, 0f)
        orb(Cyan, w * 0.85f, h * 0.15f, w * 0.28f, 0.7f, 2.1f)
        orb(Pink, w * 0.7f, h * 0.8f, w * 0.3f, 0.5f, 4.2f)

        stars.forEach { s ->
            val alpha = 0.25f + 0.55f * ((sin(time * s.speed * 3f + s.phase) + 1f) / 2f)
            drawCircle(
                color = Color.White.copy(alpha = alpha),
                radius = s.radius,
                center = Offset(s.x * w, s.y * h),
            )
        }

        val progress = time / (2f * PI.toFloat())
        meteors.forEach { m ->
            val y = ((m.phase + progress * m.speed) % 1.15f) - 0.1f
            val head = Offset(m.x * w - y * m.tilt * w * 8f, y * h)
            val tail = Offset(head.x + m.tilt * w * 2.5f, head.y - m.length * h)
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Cyan.copy(alpha = 0.55f), Color.Transparent),
                    start = head,
                    end = tail,
                ),
                start = head,
                end = tail,
                strokeWidth = 1.6f,
            )
        }
    }
}

// ── Staggered entrance reveal ────────────────────────────────────────────────
@Composable
private fun Reveal(delayMillis: Int, content: @Composable () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(delayMillis.toLong())
        visible = true
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(700)) +
            slideInVertically(tween(700, easing = FastOutSlowInEasing)) { it / 3 },
    ) {
        content()
    }
}

// ── Hero ─────────────────────────────────────────────────────────────────────
@Composable
private fun HeroSection() {
    Column(
        modifier = Modifier.widthIn(max = 1100.dp).fillMaxWidth().padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(72.dp))
        Reveal(delayMillis = 0) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                GlowingAvatar()
                GradientText("David Arribas", fontSize = 52)
                TypewriterRole()
                Text(
                    "Hi, my name is David, I'm Android developer and I love to learn about everything creating new projects.",
                    fontSize = 17.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 620.dp),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ContactButton("Email", "mailto:davidarribas.ciclos@gmail.com")
                    ContactButton("GitHub", "https://github.com/davidups")
                }
            }
        }
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
private fun GlowingAvatar() {
    val transition = rememberInfiniteTransition()
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(6_000, easing = LinearEasing)),
    )
    val glowScale by transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.18f,
        animationSpec = infiniteRepeatable(tween(2_200, easing = EaseInOutSine), RepeatMode.Reverse),
    )

    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(190.dp)
                .graphicsLayer { scaleX = glowScale; scaleY = glowScale }
                .background(
                    Brush.radialGradient(listOf(Purple.copy(alpha = 0.45f), Color.Transparent)),
                    CircleShape,
                ),
        )
        Box(
            modifier = Modifier
                .size(132.dp)
                .graphicsLayer { rotationZ = angle }
                .border(3.dp, Brush.sweepGradient(AccentGradient), CircleShape),
        )
        Image(
            painter = painterResource(Res.drawable.profile),
            contentDescription = "David Arribas",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(116.dp).clip(CircleShape),
        )
    }
}

@Composable
private fun GradientText(text: String, fontSize: Int) {
    Text(
        text,
        style = TextStyle(
            brush = Brush.linearGradient(listOf(Color.White, Purple.copy(alpha = 0.85f), Cyan)),
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
        ),
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun TypewriterRole() {
    val roles = remember {
        listOf("Android Developer", "Kotlin Enthusiast", "Compose Multiplatform Builder", "App Creator")
    }
    var text by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        var index = 0
        while (true) {
            val role = roles[index % roles.size]
            for (i in 1..role.length) {
                text = role.take(i)
                delay(65)
            }
            delay(1_600)
            for (i in role.length downTo 0) {
                text = role.take(i)
                delay(30)
            }
            delay(350)
            index++
        }
    }
    val transition = rememberInfiniteTransition()
    val cursorAlpha by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(550), RepeatMode.Reverse),
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text, fontSize = 22.sp, color = Cyan, fontWeight = FontWeight.Medium)
        Text(
            "|",
            fontSize = 22.sp,
            color = Cyan,
            modifier = Modifier.graphicsLayer { alpha = cursorAlpha },
        )
    }
}

@Composable
private fun ContactButton(label: String, url: String) {
    val uriHandler = LocalUriHandler.current
    val interaction = remember { MutableInteractionSource() }
    val hovered by interaction.collectIsHoveredAsState()
    val scale by animateFloatAsState(if (hovered) 1.07f else 1f, tween(200))
    val bgAlpha by animateFloatAsState(if (hovered) 0.35f else 0.15f, tween(200))

    Surface(
        shape = RoundedCornerShape(50),
        color = Purple.copy(alpha = bgAlpha),
        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
            brush = Brush.linearGradient(listOf(Purple, Cyan)),
        ),
        modifier = Modifier
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .hoverable(interaction)
            .pointerHoverIcon(PointerIcon.Hand)
            .clickable(interactionSource = interaction, indication = null) { uriHandler.openUri(url) },
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            color = TextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

// ── Main layout: left narrow (Languages) · right wide (Experience + Education + Projects) ──
@Composable
private fun MainColumns(compact: Boolean) {
    Box(modifier = Modifier.widthIn(max = 1100.dp).fillMaxWidth().padding(horizontal = 32.dp)) {
        if (compact) {
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                Reveal(200) { LanguagesCard() }
                Reveal(350) { ExperienceCard() }
                Reveal(500) { EducationCard() }
                Reveal(650) { ProjectsCard() }
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Reveal(200) { LanguagesCard() }
                }
                Column(
                    modifier = Modifier.weight(2f),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    Reveal(300) { ExperienceCard() }
                    Reveal(450) { EducationCard() }
                    Reveal(600) { ProjectsCard() }
                }
            }
        }
    }
}

// ── Card with hover lift + glow border ───────────────────────────────────────
@Composable
private fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    val interaction = remember { MutableInteractionSource() }
    val hovered by interaction.collectIsHoveredAsState()
    val lift by animateFloatAsState(if (hovered) -10f else 0f, tween(250))
    val borderAlpha by animateFloatAsState(if (hovered) 0.9f else 0.22f, tween(250))

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard.copy(alpha = 0.82f)),
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { translationY = lift }
            .hoverable(interaction)
            .border(
                1.dp,
                Brush.linearGradient(
                    listOf(Purple.copy(alpha = borderAlpha), Cyan.copy(alpha = borderAlpha)),
                ),
                RoundedCornerShape(20.dp),
            ),
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(28.dp)) {
            GradientText(title, fontSize = 22)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Brush.linearGradient(listOf(Purple, Cyan))),
            )
            Spacer(modifier = Modifier.height(22.dp))
            content()
        }
    }
}

// ── Languages with animated proficiency bars ─────────────────────────────────
@Composable
private fun LanguagesCard() {
    SectionCard("Languages") {
        languages.forEachIndexed { index, lang ->
            LanguageBar(lang, delayMillis = 400 + index * 120)
            if (index < languages.size - 1) Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun LanguageBar(lang: Language, delayMillis: Int) {
    var started by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(delayMillis.toLong())
        started = true
    }
    val fill by animateFloatAsState(
        targetValue = if (started) lang.fill else 0f,
        animationSpec = tween(1_100, easing = FastOutSlowInEasing),
    )

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(lang.name, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(lang.label, color = Cyan, fontSize = 13.sp)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White.copy(alpha = 0.07f)),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fill)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(Brush.horizontalGradient(listOf(Purple, Cyan))),
            )
        }
    }
}

// ── Experience timeline with company links ───────────────────────────────────
@Composable
private fun ExperienceCard() {
    SectionCard("Work Experience") {
        jobs.forEachIndexed { index, job ->
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    PulsingDot(size = 10, topPadding = 4)
                    if (index < jobs.size - 1) {
                        Box(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .width(2.dp)
                                .height(56.dp)
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Purple.copy(alpha = 0.5f), Cyan.copy(alpha = 0.15f)),
                                    ),
                                ),
                        )
                    }
                }
                Column(modifier = Modifier.padding(bottom = if (index < jobs.size - 1) 8.dp else 0.dp)) {
                    LinkText(job.company, job.url, fontSize = 16)
                    Text(job.role, color = Cyan, fontSize = 14.sp)
                    Text(job.period, color = TextSecondary, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun LinkText(text: String, url: String, fontSize: Int) {
    val uriHandler = LocalUriHandler.current
    val interaction = remember { MutableInteractionSource() }
    val hovered by interaction.collectIsHoveredAsState()

    Text(
        text,
        color = if (hovered) Cyan else TextPrimary,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize.sp,
        modifier = Modifier
            .hoverable(interaction)
            .pointerHoverIcon(PointerIcon.Hand)
            .clickable(interactionSource = interaction, indication = null) { uriHandler.openUri(url) },
    )
}

@Composable
private fun PulsingDot(size: Int, topPadding: Int) {
    val transition = rememberInfiniteTransition()
    val glow by transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.9f,
        animationSpec = infiniteRepeatable(tween(1_400, easing = EaseInOutSine), RepeatMode.Reverse),
    )
    Box(
        modifier = Modifier.padding(top = topPadding.dp).size(size.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .graphicsLayer { scaleX = glow; scaleY = glow }
                .background(Purple.copy(alpha = 0.35f), CircleShape),
        )
        Box(modifier = Modifier.size(size.dp).clip(CircleShape).background(Purple))
    }
}

// ── Education ────────────────────────────────────────────────────────────────
@Composable
private fun EducationCard() {
    SectionCard("Studies") {
        EduItem("Senior Technician in Cross-Platform Application Development")
        Spacer(modifier = Modifier.height(14.dp))
        EduItem("Technician in microcomputer systems and networks")
    }
}

@Composable
private fun EduItem(degree: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
        PulsingDot(size = 8, topPadding = 5)
        Text(degree, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 15.sp)
    }
}

// ── Projects simple list card ─────────────────────────────────────────────────
@Composable
private fun ProjectsCard() {
    SectionCard("Projects") {
        projects.forEach { proj -> ProjectRow(proj) }
    }
}

@Composable
private fun ProjectRow(proj: Project) {
    val uriHandler = LocalUriHandler.current
    val interaction = remember { MutableInteractionSource() }
    val hovered by interaction.collectIsHoveredAsState()
    val shift by animateFloatAsState(if (hovered) 8f else 0f, tween(200))
    val bgAlpha by animateFloatAsState(if (hovered) 0.1f else 0f, tween(200))
    val hasLink = proj.link != null

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { translationX = shift }
            .clip(RoundedCornerShape(10.dp))
            .background(Purple.copy(alpha = bgAlpha))
            .then(
                if (hasLink) Modifier
                    .hoverable(interaction)
                    .pointerHoverIcon(PointerIcon.Hand)
                    .clickable(interactionSource = interaction, indication = null) {
                        uriHandler.openUri(proj.link!!)
                    }
                else Modifier.hoverable(interaction)
            )
            .padding(vertical = 10.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(6.dp)
                .clip(CircleShape)
                .background(if (hovered && hasLink) Cyan else Purple),
        )
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                proj.name,
                color = if (hovered && hasLink) Cyan else TextPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Cyan.copy(alpha = 0.1f),
                ) {
                    Text(
                        "# ${proj.highlight}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        color = Cyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
            Text(proj.tech, color = TextSecondary, fontSize = 13.sp)
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 8.dp),
        color = Purple.copy(alpha = 0.12f),
        thickness = 1.dp,
    )
}

// ── Lo-fi radio toggle ───────────────────────────────────────────────────────
@Composable
private fun RadioWidget(modifier: Modifier = Modifier) {
    var playing by remember { mutableStateOf(false) }
    val interaction = remember { MutableInteractionSource() }
    val hovered by interaction.collectIsHoveredAsState()
    val scale by animateFloatAsState(if (hovered) 1.06f else 1f, tween(200))
    val borderAlpha by animateFloatAsState(if (playing || hovered) 0.9f else 0.35f, tween(250))

    Surface(
        shape = RoundedCornerShape(50),
        color = DarkCard.copy(alpha = 0.92f),
        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
            brush = Brush.linearGradient(
                listOf(Purple.copy(alpha = borderAlpha), Cyan.copy(alpha = borderAlpha)),
            ),
        ),
        modifier = modifier
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .hoverable(interaction)
            .pointerHoverIcon(PointerIcon.Hand)
            .clickable(interactionSource = interaction, indication = null) {
                playing = !playing
                if (playing) RadioPlayer.play() else RadioPlayer.pause()
            },
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            EqualizerBars(playing)
            Text(
                if (playing) "Chill radio — on air" else "Chill radio",
                color = if (playing) Cyan else TextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun EqualizerBars(playing: Boolean) {
    val transition = rememberInfiniteTransition()
    val phases = listOf(0, 150, 300).map { delayMs ->
        transition.animateFloat(
            initialValue = 0.25f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                tween(420, delayMillis = delayMs, easing = EaseInOutSine),
                RepeatMode.Reverse,
            ),
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.height(16.dp),
    ) {
        phases.forEach { p ->
            val level = if (playing) p.value else 0.25f
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .fillMaxHeight(level)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Brush.verticalGradient(listOf(Cyan, Purple))),
            )
        }
    }
}

// ── Footer ───────────────────────────────────────────────────────────────────
@Composable
private fun Footer() {
    Spacer(modifier = Modifier.height(64.dp))
    Text(
        "Built with Kotlin & Compose Multiplatform — running on WebAssembly",
        color = TextSecondary.copy(alpha = 0.6f),
        fontSize = 13.sp,
    )
    Spacer(modifier = Modifier.height(32.dp))
}
