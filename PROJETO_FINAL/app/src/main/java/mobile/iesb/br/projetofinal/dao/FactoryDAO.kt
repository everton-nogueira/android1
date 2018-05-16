package mobile.iesb.br.projetofinal.dao

import android.arch.persistence.room.Room
import android.content.Context

/**
 * Created by everton on 15/05/18.
 */
class FactoryDAO {

    companion object {
        fun getConnection(applicationContext: Context): AppDatabase? {
            return Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "room-database"
            ).allowMainThreadQueries().build()
        }

    }
}