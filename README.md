# SuperDirtMod

SuperCollider Quark that extends SuperDirt for parameter modulation of
currently running synth nodes.

This will allow you to modulate some parameter of a SuperDirt SynthDef while it
is sounding.


## Motivation

...

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
:{
let pmod = streamReplace mod
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
:}

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

...


## Contributing

Bug reports and pull requests are welcome on GitHub at the [issues
page](https://github.com/munshkr/SuperDirtMod). This project is intended to be
a safe, welcoming space for collaboration, and contributors are expected to
adhere to the [Contributor Covenant](http://contributor-covenant.org) code of
conduct.


## License

This project is licensed under GPL 3+. Refer to [LICENSE.txt](LICENSE.txt)
