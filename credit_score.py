import sys
import character_score as cs 

identifier = sys.argv[1]

score = cs.character_score(identifier)
print(f"Credit-Score: '{score}'" )