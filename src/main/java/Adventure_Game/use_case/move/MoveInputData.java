package Adventure_Game.use_case.move;

import Adventure_Game.entity.Direction;

public class MoveInputData {
    private Direction direction;

    public MoveInputData(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
