package com.example.libby_calculator

import android.content.Context
import androidx.room.*
import androidx.room.Index
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phone: String? = null,
    val email: String? = null,
    val startingAmount: Double = 0.0
)

@Entity(
    tableName = "transactions",
    foreignKeys = [ForeignKey(
        entity = Customer::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("customerId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["customerId"])]
)
data class GiftTransaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val customerId: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val amount: Double
)

data class CustomerWithTransactions(
    @Embedded val customer: Customer,
    @Relation(
        parentColumn = "id",
        entityColumn = "customerId"
    )
    val transactions: List<GiftTransaction>
) {
    val remainingAmount: Double
        get() = customer.startingAmount - transactions.sumOf { it.amount }
}

@Dao
interface GiftCardDao {
    @Transaction
    @Query("SELECT * FROM customers ORDER BY name ASC")
    fun getAllCustomers(): Flow<List<CustomerWithTransactions>>

    @Transaction
    @Query("SELECT * FROM customers WHERE name LIKE '%' || :query || '%' OR phone LIKE '%' || :query || '%' OR email LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchCustomers(query: String): Flow<List<CustomerWithTransactions>>

    @Transaction
    @Query("SELECT * FROM customers WHERE id = :customerId")
    fun getCustomerById(customerId: Int): Flow<CustomerWithTransactions?>

    @Insert
    suspend fun insertCustomer(customer: Customer): Long

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Insert
    suspend fun insertTransaction(transaction: GiftTransaction)

    @Update
    suspend fun updateTransaction(transaction: GiftTransaction)

    @Delete
    suspend fun deleteTransaction(transaction: GiftTransaction)
}

@Database(entities = [Customer::class, GiftTransaction::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun giftCardDao(): GiftCardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gift_card_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
