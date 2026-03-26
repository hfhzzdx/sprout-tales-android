package app.sprouttales.datastore

import androidx.room.*

@Entity(tableName = "stories")
data class StoryIndex(
    @PrimaryKey val id: String,
    val title: String,
    val theme: String,
    val ageRange: String,
    val isBuiltin: Boolean,
)

@Entity(tableName = "progress")
data class PlayProgress(
    @PrimaryKey val storyId: String,
    val paragraphIndex: Int,
    val offsetMs: Long
)

@Dao
interface StoryDao {
    @Query("SELECT * FROM stories ORDER BY title")
    suspend fun listAll(): List<StoryIndex>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<StoryIndex>)
}

@Dao
interface ProgressDao {
    @Query("SELECT * FROM progress WHERE storyId = :id LIMIT 1")
    suspend fun get(id: String): PlayProgress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(p: PlayProgress)
}

@Database(entities = [StoryIndex::class, PlayProgress::class], version = 1)
abstract class SproutDb : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun progressDao(): ProgressDao
}
