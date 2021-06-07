package ru.warfare.darkannihilation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class XWing(game: Game) : Sprite(game, ImageHub.XWingImg.width, ImageHub.XWingImg.height) {
    private val shootTripleTime = 180
    private var lastShoot: Long = 0
    private var radius = 350
    private val vector = Vector()
    private var now: Long = 0

    init {
        health = 5
        damage = 10

        x = randInt(0, Game.screenWidth)
        y = -height
        speedX = randInt(-3, 3)
        speedY = randInt(1, 8)

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15)

        if (Game.character == "saturn") {
            radius = 600
        }

        lastShoot = System.currentTimeMillis()
    }

    private fun shoot() {
        now = System.currentTimeMillis()
        if (now - lastShoot > shootTripleTime) {
            runBlocking {
                launch(Dispatchers.IO) {
                    lastShoot = now
                    val myX = centerX()
                    val myY = centerY()
                    val plX = game.player.centerX()
                    val plY = game.player.centerY()
                    if (getDistance(myX - plX, myY - plY) < radius) {
                        vector.makeVector(myX, myY, plX, plY, 9)
                        AudioHub.playShoot()
                        Game.allSprites.add(BulletEnemy(myX, myY, vector.angle, vector.speedX, vector.speedY))
                    }
                }
            }
        }
    }

    private fun newStatus() {
        if (Game.bosses.size != 0) {
            lock = true
        }
        health = 5
        x = randInt(0, Game.screenWidth)
        y = -height - 50
        speedX = randInt(-3, 3)
        speedY = randInt(1, 8)
        if (buff) {
            up()
        }
    }

    private fun up() {
        speedX *= 2
        speedY *= 2
    }

    override fun buff() {
        buff = true
        up()
    }

    override fun stopBuff() {
        speedX /= 2
        speedY /= 2
        buff = false
    }

    override fun getRect(): Sprite {
        return goTO(x + 15, y + 15)
    }

    override fun intersection() {
        createLargeTripleExplosion()
        AudioHub.playBoom()
        Game.score += 10
        newStatus()
    }

    override fun intersectionPlayer() {
        AudioHub.playMetal()
        createSmallExplosion()
        newStatus()
    }

    override fun check_intersectionBullet(bullet: Sprite) {
        if (rect.intersect(bullet.rect)) {
            health -= bullet.damage
            bullet.intersection()
            if (health <= 0) {
                intersection()
            }
        }
    }

    override fun empireStart() {
        lock = false
    }

    override fun update() {
        if ((x > 0) and (x < screenWidthWidth) and (y > 0) and (y < screenHeightHeight)) {
            shoot()
        }
        x += speedX
        y += speedY
        if ((x < -width) or (x > Game.screenWidth) or (y > Game.screenHeight)) {
            newStatus()
        }
    }

    override fun render() {
        Game.canvas.drawBitmap(ImageHub.XWingImg, x.toFloat(), y.toFloat(), Game.alphaPaint)
    }
}