# Mayhem

## Game logic

**Mayhem** is very loosely based on WoW Arena, but without any map or movement.

Each player (bot) is tasked with playing a team of 3 _Code Heroes_ by itself, against another player also playing 3 _Code Heroes_ in real-time.

All players will fight all other registered players in regular competition style. The player with the most wins, wins the competition.

Will you join the **May**hem?

### Heroes

Each Code Hero has 5 basis statistics and a set of unique skills

* Health - for staying alive ;)
* Power - to be able to perform skills that cost power
* Regeneration - the rate at which power is regenerated every 5 seconds
* Armor - incoming negative power/health effects are reduced by this % 
* Resistance - incoming negative power/health effects have this % chance to be reduced by 50%

A fight between players is won as soon as all _Code Heroes_ of the opponent reached 0 Health, ties are not possible.

Checkout the [HEROES](HEROES.md) For a detailed overview of our _Code Heroes_.

### Skills

Skills gain or cost power and have either positive or negative effect on one of the statistics of the selected target hero. 

Each skill will take some effort in terms of a delay between 0 and 5 seconds. 
In addition, some special skills will have cooldown associated with them, 
meaning once successfully used they cannot be re-used until their cooldown period has passed.

Note that to successfully perform a skill any associated power cost must be available to the hero both during initiation and after the delay or it will fail.

Skills consist of two categories.

#### Permanent (normal)

Typical skills will cost (or gain) a bit of power and have a permanent effect.

i.e. at the cost of 20 power do 50 damage (-50 health) to hero 5. (0, 1, 2 are your heroes; 3, 4, 5 the opponents) 

#### Temporary (buffs)

(De)buffs will have a temporary effect for its duration on the selected target hero. It cannot be reapplied on the same target until its duration passed.

i.e. at the cost of 20 power add 20 resistance to target hero 2 for 30 seconds.

Note that temporary power/health effects only temporary increase the maximum of the hero not the actual value.

#### Eligible targets

Each skill has an `allowedTarget` property that designates if the hero can apply the skill to itself and/or others.

 `self, others, all`

## Usage of this API

This API project includes a few POJO's that can be used by jackson to (de)serialize any of the JSON messages between the client and server.

If you are planning on NOT using a Java ecosystem language you can just read the protocol & message flow parts of this README below and roll your own client objects (based on these).

When using Java, the fastest option is to add this API library as a dependency by adding the following to your pom or gradle equivalent.

```xml
  <repositories>
    <repository>
      <id>mayhem-api</id>
      <name>Mayhem API</name>
      <url>https://robbert1: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@maven.pkg.github.com/Robbert1/mayhem-api</url>
    </repository>
  </repositories>
  
  <dependencies>
    <dependency>
      <groupId>ninja.robbert.mayhem</groupId>
      <artifactId>mayhem-api</artifactId>
      <version>1.1.0</version>
    </dependency>
  </dependencies>
```

If you prefer to directly include the java sources into your project just clone this repo and copy them.
 
## Protocol

* All messages must be valid **single line** json and are send over TCP/IP port 1337
* A message is complete when ended with a *carriage return*
* during an actual flush of data to/from the server, zero or more complete messages found in the buffer are processed
* likewise, the bot should also delimit messages by *carriage return* and process zero or more complete json messages only
* all messages contain a "type" field telling what sort of message it is.
* messages send *to* the server are considered *input messages*, by the server *output messages*
* all *output messages* contain a "timestamp" field (which can be handy for debugged purposes)
* updates are send at most once every 50ms and only if a state change is detected
* actions can be send anytime and skills that finished first will be processed first (no batching)

## Message Flow

### Initial connection

As soon as the player successfully connects to the server, the server will send a "welcome" message 

`{"type":"welcome","timestamp":1600000000000}`

The player should (re-)register itself using a "register" message (use your gravatar email)

`{"type":"register","name":"Mark","email":"mark@gmail.com","password":"abc123"}`

(To successfully re-register as the same player after disconnection/restart the same name and password must be supplied)

After successful registration the server will respond with a "status" message, (with status *idle* unless reconnecting mid-fight) 

A "status" *idle* message already contains the 3 heroes that the server selected for the player to play with (only their order/id varies currently, you'll all play the same heroes).

`{"type":"status","status":"idle","competitionStatus":"idle","you":[{id:0,...},{id:1,...},{id:2,...}],"timestamp":1600000000000}` 

### Fights

Once a player is scheduled to fight another player a "status" *ready* message will be send by the server, containing the heroes of both players.

`{"type":"status","status":"ready","competitionStatus":"started","you":[{id:0,...},{id:1,...},{id:2,...],"opponent":[{id:3,...},{id:4,...},{id:5,...],"timestamp":1600000000000}`

About 3 seconds later the fight will start and the server will continuously send status updates whenever a state change occurs.

`{"type":"status","status":"fighting","competitionStatus":"started","you":[{id:0,...},{id:1,...},{id:2,...],"opponent":[{id:3,...},{id:4,...},{id:5,...],"timestamp":1600000000000}` 

At this point it is up to the player to make its code heroes perform one of their mighty skills by sending an action message.

`{"type":"action","hero":0,"skill":0,"target":2,"override":false}`

Use the *override* **true** option to switch to another skill immediately and cancel any ongoing skill effort. 

If the fight takes to long its status will change to *overtime*, telling the players that their heroes will start periodically losing health.

`{"type":"status","status":"overtime",...}`
 
Once the fight is complete its status will change to *finished*

`{"type":"status","status":"finished","competitionStatus":"started",result:"win",...}`
 
Once the whole competition is complete the **competitionStatus** will change to *finished* and **competitionResult** will contain an ordered list of the results of the players.

`{"type":"status","status":"finished","competitionStatus":"finished",competitionResult:[{"name":"winbot","wins":1,"losses":0},{"name":"failbot","wins":0,"losses":1}]}`

### Example *ready* status message

```$json
{
  "type": "status",
  "status": "ready",
  "competitionStatus":"started",
  "you": [
    {
      "id": 0,
      "name": "Legacy Duster",
      "skills": [
        {
          "id": 0,
          "name": "dust mainframe",
          "power": -50,
          "delay": 1500,
          "cooldown": 0,
          "duration": 15000,
          "effect": 30,
          "type": "armor",
          "allowedTarget": "others"
        },
        {
          "id": 1,
          "name": "COBOL compiled",
          "power": -30,
          "delay": 1500,
          "cooldown": 4000,
          "duration": 5000,
          "effect": 100,
          "type": "armor",
          "allowedTarget": "others"
        },
        ...
      ],
      "buffs": {},
      "cooldowns": {},
      "currentSkill": -1,
      "currentStarted":-1,
      "maxHealth": 500,
      "maxPower": 50,
      "health": 500,
      "power": 50,
      "regeneration": 0,
      "armor": 20,
      "resistance": 0,
      "powerColor": "#008B6F"
    },
    ...
  ],
  "opponent": [
    {
      "id": 3,
      "name": "Legacy Duster",
      "skills": [
        {
          "id": 0,
          "name": "dust mainframe",
          "power": -50,
          "delay": 1500,
          "cooldown": 0,
          "duration": 15000,
          "effect": 30,
          "type": "armor",
          "allowedTarget": "others"
        },
        {
          "id": 1,
          "name": "COBOL compiled",
          "power": -30,
          "delay": 1500,
          "cooldown": 4000,
          "duration": 5000,
          "effect": 100,
          "type": "armor",
          "allowedTarget": "others"
        },
        ...
      ],
      "buffs": {},
      "cooldowns": {},
      "currentSkill": -1,
      "currentStarted":-1,
      "maxHealth": 500,
      "maxPower": 50,
      "health": 500,
      "power": 50,
      "regeneration": 0,
      "armor": 20,
      "resistance": 0,
      "powerColor": "#008B6F"
    },
    ...
  ],
  "timestamp": 1614351266004
}
```

### Example *fighting* status message

```$json
{
  "type": "status",
  "status": "fighting",
  "competitionStatus":"started",
  "you": [
    {
      "id": 0,
      "name": "Legacy Duster",
      "skills": [
        {
          "id": 0,
          "name": "dust mainframe",
          "power": -50,
          "delay": 1500,
          "cooldown": 0,
          "duration": 15000,
          "effect": 30,
          "type": "armor",
          "allowedTarget": "others"
        },
        {
          "id": 1,
          "name": "COBOL compiled",
          "power": -30,
          "delay": 1500,
          "cooldown": 4000,
          "duration": 5000,
          "effect": 100,
          "type": "armor",
          "allowedTarget": "others"
        },
        ...
      ],
      "buffs": {
        "dust mainframe": {
          "type": "armor",
          "effect": 30,
          "started": 1614716094610,
          "timeout": 1614716109610
        }
      },
      "cooldowns": {"2":1614716130018},
      "currentSkill": 1,
      "currentStarted": 1614716105339,
      "maxHealth": 500,
      "maxPower": 50,
      "health": 500,
      "power": 50,
      "regeneration": 0,
      "armor": 20,
      "resistance": 0,
      "powerColor": "#008B6F"
    },
    ...
  ],
  "opponent": [
    {
      "id": 3,
      "name": "JHipster",
      "skills": [
        {
          "id": 0,
          "name": "coffee",
          "power": -20,
          "delay": 1500,
          "cooldown": 0,
          "duration": 0,
          "effect": 25,
          "type": "health",
          "allowedTarget": "all"
        },
        {
          "id": 1,
          "name": "yogaclass",
          "power": -40,
          "delay": 4000,
          "cooldown": 0,
          "duration": 0,
          "effect": 60,
          "type": "health",
          "allowedTarget": "all"
        },
        ...
      ],
      "buffs": {
        "dust mainframe": {
          "type": "armor",
          "effect": 30,
          "started": 1614716094610,
          "timeout": 1614716109610
        }
      },
      "cooldowns": {},
      "currentSkill": 0,
      "currentStarted": 1614716105340,
      "maxHealth": 200,
      "maxPower": 300,
      "health": 96,
      "power": 170,
      "regeneration": 15,
      "armor": 30,
      "resistance": 25,
      "powerColor": "#00008B"
    },
    ...
  ],
  "timestamp": 1614351266004
}
```
