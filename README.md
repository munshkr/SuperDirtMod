# SuperDirtMod

***Experimental***: SuperCollider Quark that extends SuperDirt for parameter modulation of
currently running synth nodes.

This will allow you to modulate some parameter of a SuperDirt SynthDef while it
is sounding.


## Motivation

In TidalCycles, if you play a long sample or synth, you won't be able to apply
effects and modulate them while the sample is playing:

```haskell
d1 $ n "[0, 2, 5, 9]/2" # s "superhammond" # octave 6
   # pF "vibrato" (range 0 2 $ slow 2 saw)
```

This wlll play a single chord for 2 cycles, but the `vibrato` parameter will be
fixed to 0 the entire time.  By using the `mod1` helper function, you can
modulate the `vibrato` parameter for all synths on `d1`.

```haskell
d1 $ n "[0, 2, 5, 9]/2" # s "superhammond" # octave 6

mod1 $ segment 32
     $ pF "vibrato" (range 0 2 $ slow 2 saw)
```

The `segment` is there to discretize the `saw` function into 32 steps.

A more complete example:

```haskell
d1 $ n "[0, 2, 5, 9]/2" # s "superhammond" # octave 6

mod1 $ segment 32
     $ pF "vibrato" (range 0 2 $ slow 2 saw)
     # pF "vrate" (range 0 16 $ slow 2 tri)
     # pF "voice" (range 0 4 $ perlin)
```


## Install

Open a new document on your SuperCollider IDE and type:

```supercollider
Quarks.install("https://github.com/munshkr/SuperDirtMod");
```

After a few seconds, if everything went OK, you should see something like this
in your Post window:

```
Installing SuperDirtMod
SuperDirtMod installed
-> Quark: SuperDirtMod[0.1.0]
```

Finally recompile your class library.  Go to `Language` menu, `Recompile class
library`, or hit <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>L</kbd>.


### TidalCycles

Open your boot script file and add the following at the end of the file, but
*before* `:set prompt "tidal> "`:

```haskell
--
-- == SuperDirtMod BEGIN ==
--

-- Define mod target
modTarget = OSCTarget {oName = "SuperDirtMod", oAddress = "127.0.0.1", oPort = 57130, oPath = "/set", oShape = Nothing, oLatency = 0.02, oPreamble = [], oTimestamp = BundleStamp}

-- Define mod stream
-- Total latency = oLatency + cFrameTimespan
mod <- startTidal modTarget (defaultConfig {cCtrlListen = False, cFrameTimespan = 1/20})

-- Define pmod stream replacer and orbit helper functions
pmod = streamReplace mod
mod1 = pmod "mod1" . (|< orbit 0)
mod2 = pmod "mod2" . (|< orbit 1)
mod3 = pmod "mod3" . (|< orbit 2)
mod4 = pmod "mod4" . (|< orbit 3)
mod5 = pmod "mod5" . (|< orbit 4)
mod6 = pmod "mod6" . (|< orbit 5)
mod7 = pmod "mod7" . (|< orbit 6)
mod8 = pmod "mod8" . (|< orbit 7)
mod9 = pmod "mod9" . (|< orbit 8)
mod10 = pmod "mod10" . (|< orbit 9)
mod11 = pmod "mod11" . (|< orbit 10)
mod12 = pmod "mod12" . (|< orbit 11)

-- Reset functions
reset = pI "reset" 1
modResetAll = streamOnce mod reset

putStrLn "SuperDirtMod enabled"

--
-- == SuperDirtMod END ==
--
```

This creates a new OSC Stream for SuperDirtMod and defines the `pmod` function
to replace streams, which works similarly to `p`.  There are also some
predefined `modN` functions that accompany the `dN` functions and with their
respective orbits.


## Usage

Simply execute `SuperDirtMod.start` after starting SuperDirt.

By default, it tries to use `~dirt` as the default SuperDirt instance. You can
also pass it with via parameter:

```supercollider
var mySuperDirt = SuperDirt(2, s);
mySuperDirt.loadSoundFiles;
mySuperDirt.start;

SuperDirtMod.start(superDirt: mySuperDirt)
```

If you silence a modulation stream, you will notice that parameters will not go
to default values, but instead keep using the last value when the stream was
silenced.

To reset all modulation parameters back to default values, you can use
`modResetAll`.


## Known limitations

* You can't modulate the parameters of specific synths. All running synths
  playing on some orbit are affected by the parameter modulation.

* If you silence a `pmod` stream, the last values will not be reset. For now,
  you can call `modResetAll` on TidalCycles or `SuperDirtMod.reset` on SC to
  reset the state.


## Contributing

Bug reports and pull requests are welcome on GitHub at the [issues
page](https://github.com/munshkr/SuperDirtMod). This project is intended to be
a safe, welcoming space for collaboration, and contributors are expected to
adhere to the [Contributor Covenant](http://contributor-covenant.org) code of
conduct.


## License

This project is licensed under GPL 3+. Refer to [LICENSE.txt](LICENSE.txt)
