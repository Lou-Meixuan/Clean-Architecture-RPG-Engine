package Adventure_Game.use_case.move;

import Adventure_Game.entity.AdventureGame;

public interface MoveGameDataAccessInterface {
    AdventureGame getGame();

    void saveGame(AdventureGame game);
}
