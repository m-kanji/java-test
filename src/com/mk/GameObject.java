package com.mk;

import com.mk.shape.Shape;

enum Type
{
    Null,
    Asteroid,
    Block,
    UFO,
    YourBullet,
    //EnemBullet,
    //Player
}

public class GameObject
{
    Shape shape;
    Type type;
    int health;

    GameObject(Shape shape, Type type, int health)
    {
        this.shape = shape;
        this.type = type;
        this.health = health;
    }

    GameObject(Shape shape, Type type)
    {
        this.shape = shape;
        this.type = type;
        this.health = 100;
    }

    public Shape getShape() { return shape; }
    public Type getType() { return type; }
    public int getHealth() { return health; }
    public void setShape(Shape shape) { this.shape = shape; }
    public void setType(Type type) { this.type = type; }
    public void setHealth(int health) { this.health = health; }
    public void changeHealth(int change) { health += change; }
}
