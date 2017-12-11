

=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: andrewme
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
Lunar Lander Simulation
    Andrew (Tzvi) Merczynski-Hait	
    CIS120 Fall 2017

==================
=: Introduction :=
==================

This game is based off of the classic game Lunar Lander, which can be played
here (http://moonlander.seb.ly/).

I extended it to more closely model real physics and spaceship controls.

Additionally, I added some features designed to simulate the training for a lunar
mission during NASA's Apollo program, in particular the Apollo 11 landing.

For some cool resources regarding the program that relate to my game
see the links in the External Resources section.


===========================
=: Compiling and Running :=
===========================


To compile the game from source and run the game, first download the zip file,
and then run the following commands in a terminal:

cd $(DOWNLOAD_DIRECTORY)
mkdir $(DIRECTORY_TO_PLACE_UNZIPPED_GAME)
unzip $(ZIP_FILE) -d $(DIRECTORY_TO_PLACE_UNZIPPED_GAME)
cd $(DIRECTORY_TO_PLACE_UNZIPPED_GAME)
make run

You will then be able to choose if you are playing as the pilot or the Simsup 
(see gameplay instructions below). Make sure that you start your pilot game before
someone attempts to connect as the simsup.  

The pilot game runs a server on port 8080, so to allow simsups to connect you
must make sure port 8080 is opened in your computer's firewall and that your IP 
address/host is accesible to the simsup's computer.

To run both the pilot game and the simsup game on the same computer simply enter 
localhost for the hostname. 


====================================
=: Detailed Gameplay Instructions :=
====================================

As the pilot-in-training, your objective is to land the LEM (lunar excursion module) 
without crashing. You crash if you hit the ground too fast (vertically or horizontally) 
or at too high an angle. Additionally, you have limited fuel, and if you run out 
of fuel you will likely crash.

In the easy mode the throttle controls are simply "on" and "off", and you move directly
to an angular position when you thrust left or right. In hard mode the controls behave
more realistically and you must control your angular velocity and manually throttle up and down.
(See the instruction screen for detailed key assignments).

A Simulation Supervisor can connect to your instance of the game and send you various errors
that will affect your ability to complete the landing, and you will need to handle them as appropriate.
Sometimes the best course of action is to fly back up to the Command Module (not shown) 
in a controlled fasion and reset, essentially aborting.

You can be added to the high score list at the end of the game if you land with enough 
extra fuel.


===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an approprate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

	The four concepts I chose to implement were:
		1) File I/O
		2) Dynamic Dispatch/Subtyping
		3) Collection Use
		4) Network I/O
	Additionally, the model that contains the physics behind the lunar lander is fully  
	encapsulated and seperate from all Swing components, but I did not test the model.
	Additionally, my game and network components each involve multiple threads.

	1) I implemented a highscores file, where if you land with more fuel left than 
	the last few players you can be added to the highscore list. The score is 
	how much fuel you had left at landing. This state is persistent across games. 
	The highscore file continues to save every player that every reached the highscores
	fuel left, even if he is no longer eligible to be displayed due to other players doing 
	better. Highscores is appropirate to save between games, so it is fitting for file IO
	to save the state. I stored the file as a semicolon seperated list with each line
	containing one entry of player and his score.

	2) The possible errors that the simsup can send to a connected pilot are all subtypes
	of an abstract class "Error". It is an abstract class and not an interface so that I can
	override CompareTo as well as write a static method to create new errors based on error codes.
	The methods getError and isComputerError() are essentially methods that could just
	return values of fields; nevertheless they allow me to use Error as the type when
	sending errors over the network from the simsup to the pilor.
	The main use of dynamic dispatch is by writing each subclass to override causeFailure()
	and undoFailure(). Thus, when handling the packets that sending errors over the network
	I can simply call error.causeFailure() and error.undoFailure() for each instance of
	subtypes of error, and what the error does will depend on the dynamic type of the error
	that was sent from the simsup. 

	3) When the simsup sends an error over the network I add the error to map of type
	Map<Error, Integer>, where the interger represents the number of times the simsup has
	sent a specific error. I chose to use a map because it is the natural way to store a 
	relaitionship between keys and values where each value could apply to multiple keys,
	but each key has just one value. This map is used to display to the simsup how many
	times he has sent an error. This value is persistent through when the pilot resets the
	game but not across game restarts.

	4) For network I/O I have the pilot game create a server thread that listens for
	any incoming connections from a simsup game. Once a connection has been established
	the pilot continuosly writes packets that contain telemtry information which is then displayed on
	the simsup's screen. Each time the simsup clicks on an error button that error is sent
	to the pilot's list of errors to cause. Sending this data over the network is a good
	way to link 2 simultaneous players, simulating the link between the simulation supervisor
	and the pilot in the simulator that occurred during training for the Apollo missions.

	On the simsup side used a BlockingQueue to transfer the errors that have been cliked to
	the network handling. I did this to make it easy for the network handling part to block/wait
	until an error is actually avaliable to send.

	The packets that each side sends contain iformation for all the possible values that may
	be sent. However, when some fields are not necessary the packet is filled with default
	values. I.e. the pilot sends null in the error field, as the simsup does not display what
	errors the pilot has currently and the simsup sends 0 for all the telemetry values.
	I did this to allow me to use the same packet for both directions. They are 
	formatted as a string containing values seperated by "$".

	When one player disconnects the other player receives a message that the first has
	disconnected and the game then quits.



=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

	  - Game is the main class that loads the either the pilot game or the simsup game

	  - SimSupGame and PilotGame each handle the respective startup screens, start the 
	  games and start the networking component threads for each.

	  - NetworkHandler defines the sockets that are shared by the writer and reader for
	  each game, and provides a method for checking if the connection has been established.

	  - Server and Client setup and start the networking threads respectively.

	  - ServerReader, ServerWriter, ClientWriter, and ClientReader are called from Server
	  and Client to create the threads that simultaneuously read and write for each game.

	  - NetworkPacket provides methods to handle the packets sent over the network.

	  - TelemetryPanel provides the display for pilot and simsup that carries velocity,
	  fuel information etc. i.e. telemetry.

	  - ErrorButtonPanel provides the buttons for the simsup to send errors

	  - LunarModel provides the methods for interfacing with the physics engine. For 
	  example the game can call throttle, thrustLeft, thrustRight, methods on a LunarModel

	  - GameState is state of the game, that holds all the state updated based on the LunarModel,
	   network events or button presses. Each display also uses information from GameState.
	   In each game (pilot or simsup) every thread shares one GameState.

	  - Canvas is what holds the game display area for the pilot, and is linked to a
	  	LunarModel and GameState

	  - HighScoreHandler handles the File IO for high scores.

	  - HighScore is a datatype for holding a highscore representation (player and score)

	  - HighScorePanel is a panel for displaying the highscores.

	  - Error and all its subtypes represent the errors that can be caused by the simsup


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

	  I had some difficulty implementing how to have the network actions all happen 
	  at the same time as each other and as the game, but eventually decided on just
	  making each concurrent action a seperate thread.

	  Also, I had a lot of issues with using the physics engine that I chose, as there
	  was rather poor documentation.
	  It also led to difficulties that I was using the physics engine to draw
	  invisible shapes that reacted to physics, and then I needed to use Swing to 
	  draw actual shapes that had to be linked to the position of those physics shapes.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

	  Throughout I tried to keep every aspect of the game seperate from each other, 
	  and only allowed one access to another when necessary. For instance, the LunarModel
	  can exist completely independently of any swing drawings. And the GameState is also
	  independent of a LunarModel or of any Swing component. In this game, Canvas is 
	  what does the drawing and keeps the game ticking forward, it has access to the GameState
	  and the LunarModel (the methods of the game's specific instance, not their private fields). 

	  Similarly, the netwok components take in the relevant GameState instance, and the
	  panels they will update as arguments, rather than being inherently linked to the main
	  game and Swing components.

	  I'm overall happy with the current organization, but perhaps I would condense some of
	  the networking classes I have.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

  	LEM Sprite: https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/2e012515121165.55f33aae45585.png
  		With modifications
  	Flame Sprite: http://images.hellokids.com/_uploads/_tiny_galerie/20120416/d7g_how-to-draw-fire-for-kids-step-3.jpg
  		With modifications

  	Physics Engine: http://www.jbox2d.org/
  					https://github.com/jbox2d/jbox2d
  		Along with its documentation, and helpful pages from http://www.iforce2d.net/b2dtut/


- Additionally, here are some links to information about the Apollo program, 
misison control training, the Apollo Guidance Computer and the Apollo 11 descent:

	https://en.wikipedia.org/wiki/Apollo_program
	https://blogs.nasa.gov/waynehalesblog/tag/sim-sup/
	https://waynehale.wordpress.com/2014/10/21/practicing-for-disaster/
	https://history.nasa.gov/SP-350/ch-11-4.html
	https://en.wikipedia.org/wiki/Apollo_11#Lunar_descent
	https://en.wikipedia.org/wiki/Apollo_Guidance_Computer#PGNCS_trouble
	https://www.hq.nasa.gov/alsj/a11/a11.1201-pa.html
	https://spaceflight.nasa.gov/history/apollo/apollo11/audio/11-03353.mp3 








