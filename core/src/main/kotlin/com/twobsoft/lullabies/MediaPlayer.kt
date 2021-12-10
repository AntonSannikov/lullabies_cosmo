package com.twobsoft.lullabies

object MediaPlayer {

    var serviceApi: ServicesCoreInterface? = null

    val playlist = hashMapOf<Int, String>(
        0 to "1. Twinkle Twinkle Little Star",
        1 to "2. Don Giovanni - Madamina, il catalogo è questo",
        2 to "3. Symphony No.11 in D major",
        3 to "4. Don Giovanni - Andiam, Andiam",
        4 to "5. Le nozze di Figaro - Arietta",
        5 to "6. Violin - Sonata No. 24",
        6 to "7. Piano Sonata - Alla turca",
        7 to "8. Don Giovanni - Ho capito, Signor si",
        8 to "9. Beethoven - Moonlight sonata (special for A.)",
        9 to "10. The magic flute - Trois beaux enfants fins",
        10 to "11. Twinkle Little Star (Christmas Version) - Paid option",
        11 to "12. Variations in F major - Paid option",
        12 to "13. Eine Kleine Nachtmusik - Paid option",
        13 to "14. Kirie Eleison - Paid option",
        14 to "15. Sinfonia Concertante - Paid option",
        15 to "16. Serenade in D major Haffner - paid option"
    )

    fun play(number: Int, isSwitching: Boolean = false) {
        serviceApi!!.playMusic(number, isSwitching)
    }

    fun initCallbacks() {
        serviceApi!!.initPlayCallback(::toPlay)
        serviceApi!!.initPauseCallback(::toPause)
        serviceApi!!.initPreviousCallback(::toPrevious)
        serviceApi!!.initNextCallback(::toNext)
    }


    fun toPause() {
        println("CORE PAUSE")
    }

    fun toPlay() {
        println("CORE PLAY")
    }

    fun toNext() {
        println("CORE NEXT")
    }

    fun toPrevious() {
        println("CORE PREV")
    }

}