package com.gabus.dev.comarca.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gabus.dev.comarca.data.dao.CardDao;
import com.gabus.dev.comarca.data.dao.FactionDao;
import com.gabus.dev.comarca.data.dao.PlayerDeckDao;
import com.gabus.dev.comarca.data.dao.RunDao;
import com.gabus.dev.comarca.data.entity.CardEntity;
import com.gabus.dev.comarca.data.entity.FactionEntity;
import com.gabus.dev.comarca.data.entity.PlayerDeckEntity;
import com.gabus.dev.comarca.data.entity.RunEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
    entities = {
        FactionEntity.class,
        CardEntity.class,
        PlayerDeckEntity.class,
        RunEntity.class
    },
    version = 1,
    exportSchema = true
)
public abstract class ComarcaDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "comarca.db";
    private static volatile ComarcaDatabase INSTANCE;

    public abstract FactionDao factionDao();
    public abstract CardDao cardDao();
    public abstract PlayerDeckDao playerDeckDao();
    public abstract RunDao runDao();

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static ComarcaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ComarcaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ComarcaDatabase.class,
                            DATABASE_NAME
                    )
                    .addCallback(roomDatabaseCallback)
                    .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                populateDatabase(INSTANCE);
            });
        }
    };

    private static void populateDatabase(ComarcaDatabase database) {
        FactionDao factionDao = database.factionDao();
        CardDao cardDao = database.cardDao();

        // Create factions
        FactionEntity humanos = new FactionEntity(
            "Humanos",
            "#7ba4d4",
            "Soldados disciplinados con armadura y estrategia",
            0
        );
        FactionEntity enanos = new FactionEntity(
            "Enanos",
            "#e8a630",
            "Forjadores resistentes con poderosas defensas",
            0
        );
        FactionEntity ogros = new FactionEntity(
            "Ogros",
            "#6bbd4a",
            "Criaturas salvajes con fuerza bruta descomunal",
            0
        );
        FactionEntity elfos = new FactionEntity(
            "Elfos",
            "#b07cd8",
            "Seres etéreos con magia y destreza",
            0
        );

        long humanosId = factionDao.insertFaction(humanos);
        long enanosId = factionDao.insertFaction(enanos);
        long ogrosId = factionDao.insertFaction(ogros);
        long elfosId = factionDao.insertFaction(elfos);

        // Create cards - Humanos
        cardDao.insertAllCards(java.util.Arrays.asList(
            new CardEntity("Vanguardia", humanosId, 1, 4, 3, "Infantería humana avanzada", null, 0),
            new CardEntity("Cazador", humanosId, 1, 5, 1, "Arquero de élite", null, 0),
            new CardEntity("Comandante", humanosId, 2, 3, 5, "Líder que fortalece al escuadrón", null, 0),
            new CardEntity("Bardo", humanosId, 2, 1, 3, "Sana y inspire aliados", null, 0)
        ));

        // Create cards - Enanos
        cardDao.insertAllCards(java.util.Arrays.asList(
            new CardEntity("Herrero", enanosId, 1, 3, 5, "Defensa de montaña", null, 0),
            new CardEntity("Berserker", enanosId, 2, 6, 2, "Ataque devastador con hacha", null, 0),
            new CardEntity("Guardián", enanosId, 2, 2, 7, "Muro impenetrable", null, 0),
            new CardEntity("Minero", enanosId, 1, 3, 3, "Roba recursos del enemigo", null, 0)
        ));

        // Create cards - Ogros
        cardDao.insertAllCards(java.util.Arrays.asList(
            new CardEntity("Tosco", ogrosId, 1, 5, 2, "Golpe brutal", null, 0),
            new CardEntity("Fangoso", ogrosId, 2, 7, 3, "Lanza barro cegador", null, 0),
            new CardEntity("Carroñero", ogrosId, 1, 4, 1, "Ataca y roba vida", null, 0),
            new CardEntity("Jefe Trol", ogrosId, 3, 8, 4, "El más temible de la manada", null, 0)
        ));

        // Create cards - Elfos
        cardDao.insertAllCards(java.util.Arrays.asList(
            new CardEntity("Duelista", elfosId, 1, 4, 2, "Espada ágil y precisa", null, 0),
            new CardEntity("Mago", elfosId, 2, 6, 1, "Hechizo arcano devastador", null, 0),
            new CardEntity("Sanador", elfosId, 2, 1, 4, "Restaura vida con magia ancestral", null, 0),
            new CardEntity("Druida", elfosId, 2, 3, 3, "Invoca la fuerza de la naturaleza", null, 0)
        ));
    }
}
