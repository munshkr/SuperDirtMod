# SuperDirtMod.sc

SuperCollider Quark that extends SuperDirt for parameter modulation of
currently running synth nodes.

This will allow you to modulate some parameter of a SuperDirt SynthDef while it
is sounding.  For example:

```haskell
d1 $ s "bev/8" #~ lpf (range 300)

dm1 # orbit 1
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


## Usage

...


## Contributing

Bug reports and pull requests are welcome on GitHub at the [issues
page](https://github.com/munshkr/SuperDirtMod.sc). This project is intended to
be a safe, welcoming space for collaboration, and contributors are expected to
adhere to the [Contributor Covenant](http://contributor-covenant.org) code of
conduct.


## License

This project is licensed under GPL 3+. Refer to [LICENSE.txt](LICENSE.txt)
