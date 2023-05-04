# MM_DirectionalVelocity
A server plugin that adds a directional velocity mechanic to [MythicMobs](https://git.mythiccraft.io/mythiccraft/MythicMobs)

put the jar in the plugins folder

## Usage example:
  I don't know because I don't use mythic mobs, but heres a snippet of what I used to test it (Modified from [this wiki page](https://git.mythiccraft.io/mythiccraft/MythicMobs/-/wikis/skills/mechanics/projectile))
  ```
  IceBolt:
    Skills:
    - projectile{onTick=IceBolt-Tick;onHit=IceBolt-Hit;v=20;i=1;hR=1;vR=1;hnp=true}


IceBolt-Tick:
    Skills:
    - effect:particles{p=snowballpoof;amount=20;speed=0;hS=0.2;vS=0.2} @origin

IceBolt-Hit:
    Skills:
    - damage{a=10}
    - dirvelocity{mode=SUBTRACT;forward=1.0}

```

There are 5 modes: `ADD`, `SUBTRACT`, `MULTIPLY`, `DIVIDE`, and `SET`.

The three directions are `forward`, `right`, and `up`, all set to `0` by default
(`f`, `r`, and `u` work as well)


The values are all relative to the direction of the target entity, so to launch the entity forward, (whichever direction the entity is facing), you would use
`- dirvelocity{mode=ADD;forward=2.0}`

## building
`./gradlew build`

jar appears in build/libs/
