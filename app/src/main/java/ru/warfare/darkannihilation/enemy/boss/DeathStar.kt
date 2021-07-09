package ru.warfare.darkannihilation.enemy.boss

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.warfare.darkannihilation.*
import ru.warfare.darkannihilation.Constants.BOSS_HEALTH
import ru.warfare.darkannihilation.Constants.BOSS_SHOOT_TIME
import ru.warfare.darkannihilation.base.Sprite
import ru.warfare.darkannihilation.bullet.BulletBoss
import ru.warfare.darkannihilation.hub.AudioHub
import ru.warfare.darkannihilation.hub.ImageHub
import ru.warfare.darkannihilation.math.Math
import ru.warfare.darkannihilation.systemd.Game

class DeathStar(game: Game) : Sprite(game, ImageHub.bossImage) {
    private var lastShoot = System.currentTimeMillis()
    private var now: Long = 0
    private var hp: Float = 10f

    init {
        health = BOSS_HEALTH.toInt()
        speedY = 1
        speedX = 10
        isPassive = true
        x = Math.randInt(0, screenWidthWidth)
        y = -800
        ImageHub.loadPortalImages()
        recreateRect(x + 20, y + 20, right() - 20, bottom() - 20)
    }

    private fun shoot() {
        now = System.currentTimeMillis()
        if (now - lastShoot > BOSS_SHOOT_TIME) {
            runBlocking {
                launch(Dispatchers.IO) {
                    lastShoot = now
                    val r = right() - 115
                    val y20 = y + 40
                    for (i in 1..3) {
                        Game.allSprites.add(
                            BulletBoss(
                                r,
                                y20,
                                i
                            )
                        )
                    }
                    AudioHub.playShoot()
                }
            }
        }
    }

    private fun killAfterFight() {
        if (HardThread.job == 0) {
            HardThread.job = 10
            createSkullExplosion()
            Game.bosses.remove(this)
            Game.allSprites.remove(this)
        }
    }

    override fun getRect(): Sprite {
        return newRect(x + 20, y + 20)
    }

    override fun check_intersectionBullet(bullet: Sprite) {
        if (intersect(bullet)) {
            health -= bullet.damage
            bullet.intersection()
        }
    }

    override fun update() {
        if (y >= 35) {
            x += speedX
            if (x < -width) {
                if (Math.randInt(1, 2) == 1) {
                    speedX = -speedX
                } else {
                    x = Game.screenWidth
                }
            }
            if (x > Game.screenWidth) {
                if (Math.randInt(1, 2) == 1) {
                    speedX = -speedX
                } else {
                    x = -width
                }
            }
            shoot()

            if (health <= 0) {
                killAfterFight()
            }
        } else {
            if (y == -600) {
                AudioHub.restartBossMusic()
                AudioHub.pauseBackgroundMusic()
                Game.gameStatus = 5
            }
            y += speedY
        }
    }

    override fun render() {
        Game.canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)

        hp = if (hp > 4) {
            ((health / BOSS_HEALTH.toFloat()) * 140)
        } else {
            4f
        }
        Game.canvas.drawRect(
            (centerX() - 70).toFloat(),
            (y - 10).toFloat(),
            (centerX() + 70).toFloat(),
            (y + 5).toFloat(),
            Game.scorePaint
        )
        Game.canvas.drawRect(
            (centerX() - 68).toFloat(),
            (y - 8).toFloat(),
            (centerX() - 72 + hp),
            (y + 3).toFloat(),
            Game.topPaintRed
        )
    }
}