package com.gabus.dev.comarca.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.gabus.dev.comarca.data.dao.RunDao;
import com.gabus.dev.comarca.data.db.ComarcaDatabase;
import com.gabus.dev.comarca.data.entity.RunEntity;

import java.util.List;

public class RunRepository {

    private final RunDao runDao;

    public RunRepository(Application application) {
        ComarcaDatabase db = ComarcaDatabase.getDatabase(application);
        runDao = db.runDao();
    }

    public LiveData<RunEntity> getActiveRun() {
        return runDao.getActiveRun();
    }

    public RunEntity getActiveRunSync() {
        return runDao.getActiveRunSync();
    }

    public LiveData<List<RunEntity>> getRecentRuns(int limit) {
        return runDao.getRecentRuns(limit);
    }

    public LiveData<RunEntity> getRunById(long runId) {
        return runDao.getRunById(runId);
    }

    public long createNewRun(int maxHP) {
        RunEntity run = new RunEntity(maxHP);
        return runDao.insertRun(run);
    }

    public long createNewRun() {
        RunEntity run = new RunEntity();
        return runDao.insertRun(run);
    }

    public void updateRun(RunEntity run) {
        ComarcaDatabase.databaseWriteExecutor.execute(() -> {
            run.lastSaveDate = System.currentTimeMillis();
            runDao.updateRun(run);
        });
    }

    public void endRun(long runId) {
        ComarcaDatabase.databaseWriteExecutor.execute(() ->
            runDao.endRun(runId)
        );
    }

    public void endAllActiveRuns() {
        ComarcaDatabase.databaseWriteExecutor.execute(() ->
            runDao.endAllActiveRuns()
        );
    }
}
