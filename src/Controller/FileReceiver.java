package Controller;

import java.io.File;
import java.io.IOException;

public interface FileReceiver {
    void updateAllPawns(File loadGame) throws IOException;
}
