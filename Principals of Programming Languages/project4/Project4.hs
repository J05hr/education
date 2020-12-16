module Project4 where
-- Release 2

data DFA state symbol = DFA
    { alphabet :: [symbol]
    , states   :: [state]
    , initial  :: state
    , transit  :: state -> symbol -> state
    , accept   :: state -> Bool
    }

data ABC = A | B | C deriving (Show, Read, Eq, Ord)

//from class-assignment
accepts :: DFA state symbol -> [symbol] -> Bool
accepts d = accept d . foldl (transit d) (initial d)

-- a DFA that accepts all strings over the alphabet {A,B,C} containing at most
-- two A's
atMost2As :: DFA Int ABC
atMost2As = undefined


-- a DFA that accepts all strings over the alphabet {A,B,C} containing and odd
-- number of A's
oddAs :: DFA Int ABC
oddAs = undefined


-- a DFA that accepts all strings over the alphabet {A,B,C} containing the
-- sequence A,B,C
hasABC :: DFA Int ABC
hasABC = undefined


-- change this to True if you are attempting the extra credit
extra_credit = False

-- multiplex d1 d2 returns a DFA that reads a string of symbols intended for
-- either d1 or d2. The DFA accepts a string if d1 accepts the portion of the
-- string marked Left and d2 accepts the portion marked Right

multiplex :: DFA s1 a1 -> DFA s2 a2 -> DFA () (Either a1 a2)
-- This type is a placeholder. You will need to change the state type for the
-- return DFA to something else if you attempt this problem.

multiplex d1 d2 = undefined
