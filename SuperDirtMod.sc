SuperDirtMod {
	classvar responders;
	classvar lastValues;
	classvar superDirt;
	classvar started = false;

	*start { |port=57130, superDirtInst=nil|
		var senderAddr = NetAddr("127.0.0.1");

		if (superDirtInst.isNil) {
			superDirt = superDirtInst;
		} {
			"[SuperDirtMod] Using SuperDirt.default".postln;
			superDirt = SuperDirt.default;
		};

		this.stop;
		responders.add(
			OSCFunc({ |msg, time, tidalAddr|
				var params, orbitIndex, orbit;

				params = msg[1..];
				orbitIndex = params.asDict[\orbit];
				orbit = superDirt.orbits[orbitIndex];

				params.pairsDo { |param, value|
					[\cps, \cycle, \delta, \orbit].any { |p| p == param }.not.if {
						// "set param % to %".format(param, value).postln;
						orbit.server.sendMsg("/n_set", orbit.group, param, value);
						orbit.defaultParentEvent[param] = value;
						lastValues[param] = value;
					};
					// [param, ~sdeLastValues[param]].postln;
				}
			}, '/set', senderAddr, recvPort: port).fix;
		);

		started = true;

		"[SuperDirtMod] Started. Listening for messages on port %".format(port).postln;
	}

	*stop {
		if (responders.isNil) {
			responders = List.new;
		};
		responders.do { |x| x.free };
		responders.clear;
		lastValues = ();
	}

	*reset {
		if (started == false) {
			^this;
		};

		superDirt.orbits.do { |orbit|
			lastValues.keysDo { |param|
				orbit.defaultParentEvent.delete(param);
			}
		}
	}
}