package com.davidups.web

private fun audioCreate(src: String): JsAny =
    js("(() => { const a = new Audio(src); a.loop = true; a.volume = 0.35; return a; })()")

private fun audioPlay(a: JsAny): Unit = js("void a.play()")

private fun audioPause(a: JsAny): Unit = js("a.pause()")

actual object RadioPlayer {
    private val audio: JsAny by lazy {
        audioCreate("https://ice1.somafm.com/groovesalad-128-mp3")
    }

    actual fun play() = audioPlay(audio)

    actual fun pause() = audioPause(audio)
}
