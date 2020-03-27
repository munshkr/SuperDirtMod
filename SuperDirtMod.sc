Flok {
	classvar <>started = false;

	*start { |port=57200|
		var net;

		if (this.started == true) {
			"Flok has already been started".postln;
			^this;
		};

		// Open UDP port. Only listen to OSC messages from localhost.
		net = NetAddr.new("127.0.0.1");
		thisProcess.openUDPPort(port);

		OSCFunc({ |msg, time, addr, port|
			var body = msg[1].asString;

			// Interpret code sent
			defer {
				thisProcess.interpreter.cmdLine_(body).interpretPrintCmdLine;
			};
		}, '/flok', net).permanent_(true);

		this.started = true;

		"Flok started. Listening for messages on port %...".format(port).postln;
	}
}
