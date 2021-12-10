package com.twobsoft.lullabies.android

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.twobsoft.lullabies.R


class CreateNotification {

    companion object {
        val CHANNEL_ID = "channel1"
        val ACTION_PLAY = "action_play"
        val ACTION_PREVIOUS = "action_previous"
        val ACTION_NEXT = "action_next"

        var notification: Notification?=null

        fun getMediaStyle(size: Int): androidx.media.app.NotificationCompat.MediaStyle {

            if (size == 1) {
                return androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0)
            }
            else if (size == 2){
                return androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0, 1)
            } else {
                return androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0, 1, 2)
            }
        }




        fun getActions(pos: Int, size: Int, context: Context, playButton: Int): ArrayList<NotificationCompat.Action> {
            val result = arrayListOf<NotificationCompat.Action>()

            // previous
            val intentPrevious = Intent(context, NotificationActionService::class.java)
                .setAction(ACTION_PREVIOUS)
            val pendingIntentPrevious = PendingIntent.getBroadcast(
                context,
                0,
                intentPrevious,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            // play
            val intentPlay = Intent(context, NotificationActionService::class.java)
                .setAction(ACTION_PLAY)
            val pendingIntentPlay = PendingIntent.getBroadcast(
                context,
                0,
                intentPlay,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            // next
            val intentNext = Intent(context, NotificationActionService::class.java)
                .setAction(ACTION_NEXT)
            val pendingIntentNext = PendingIntent.getBroadcast(
                context,
                0,
                intentNext,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            if (pos == 0) {
                result.add(NotificationCompat.Action(
                    playButton,
                    "Play", pendingIntentPlay)
                )
                result.add(NotificationCompat.Action(
                    R.drawable.ic_baseline_skip_next_24,
                    "Next", pendingIntentNext)
                )
            } else if (pos == size-1) {
                result.add(NotificationCompat.Action(
                    R.drawable.ic_baseline_skip_previous_24,
                    "Previous", pendingIntentPrevious)
                )
                result.add(NotificationCompat.Action(
                    playButton,
                    "Play", pendingIntentPlay)
                )
            } else {
                result.add(NotificationCompat.Action(
                    R.drawable.ic_baseline_skip_previous_24,
                    "Previous", pendingIntentPrevious)
                )
                result.add(NotificationCompat.Action(
                    playButton,
                    "Play", pendingIntentPlay)
                )
                result.add(NotificationCompat.Action(
                    R.drawable.ic_baseline_skip_next_24,
                    "Next", pendingIntentNext))

            }
            return  result
        }




        @SuppressLint("UnspecifiedImmutableFlag")
        fun createNotification(context: Context, track: Track, playButton: Int, pos: Int, size: Int) {


            val notificationManagerCompat = NotificationManagerCompat.from(context)
            val mediaSessionCompat = MediaSessionCompat(context, "tag")
            mediaSessionCompat.isActive = true

//            val icon = BitmapFactory.decodeResource(context.resources, R.mipmap.launcher_icon_foreground)

            val mediaActions = getActions(pos, size, context, playButton)
            val mediaStyle = getMediaStyle(mediaActions.size)

            mediaStyle.setMediaSession(mediaSessionCompat.sessionToken)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_music_note_24)
//                .setLargeIcon(icon)
                .setContentTitle(track.title)
                .setContentText(track.artist)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)

            mediaActions.forEach {
                builder.addAction(it)
            }

            builder.setStyle(androidx.media.app.NotificationCompat.MediaStyle())
            builder.priority = NotificationCompat.PRIORITY_LOW

            notification = builder.build()
            notificationManagerCompat.notify(1, notification!!)

        }
    }


}