package tp1.logic;

public abstract class MovingObject extends GameObject{
    protected int dir;
    protected boolean isFalling;
    

    public MovingObject(int dir, boolean isFalling, Position pos, GameWorld world){
        super(pos,world);
        this.dir = dir;
        this.isFalling = isFalling;
    }

    protected MovingObject(){
        super();
    }

    public void update(GameObject g){
        move(g);
    }

    public void move(GameObject g){
        if(!g.isAlive()){
            return;
        }

        Position below = Position.move(pos, Action.DOWN); //Posición de debajo, primera comprobación para caída automática
        boolean haySuelo = world.isSolid(below); //Comprobamos si hay suelo para siguiente mov automático

        if(!haySuelo){
            g.pos = below;

            if(!world.isInsideBoard(Position.move(below, Action.DOWN))){ //Si cae y no hay land, muere el goomba
                g.alive = false;
            }
            return;     
        }

        Position next = Position.move(pos, Action.LEFT); //Si hay suelo, comenzamos a moverlo a la izquierda (dir = -1)
        boolean hayObstaculo = world.isSolid(next); //Si hay obstaculo, movemos a la dirección contraria (derecha)

        if(hayObstaculo){
            Position thereIsObstacle = Position.move(pos, Action.RIGHT);
            g.pos = thereIsObstacle;

        }else{
            g.pos = next;
        }
        world.doInteractionsFrom(g);
    }
}
