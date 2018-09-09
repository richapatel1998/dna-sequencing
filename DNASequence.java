package mini1;

/**
 * Simple model of a strand of DNA.  An instance of this class encapsulates
 * a string of characters.  A character is called <em>valid</em> if it
 * is one of the the letters 'A', 'C', 'G', 'T' (uppercase) and a DNASequence
 * object is called <em>valid</em> if all its characters are valid.
 * The characters 'A' and 'T' are said to be <em>complements</em> of each 
 * other and likewise the characters 'C' and 'G' are complements.  
 * Complementary characters are said to <em>bond</em> with each other.
 * The main operations on this class are for the purpose of determining the 
 * number and locations of bonds that one sequence can form with another
 * depending how they are aligned (shifted) with each other.
 * <p>
 * However, it is entirely possible to construct a DNASequence object
 * containing invalid characters, and all operations should work
 * correctly for arbitrary characters.  Note that a character other than
 * 'A', 'C', 'G', or 'T' is never considered to bond with another character.
 */
public class DNASequence
{
  /**
   * String of data for this sequence. 
   */
  private String data;
  
  /**
   * Constructs a DNASequence object with the given string of data;
   * this constructor does not check whether the given string
   * is valid (see the method allValid).
   * @param givenData
   *   string of characters for this DNASequence
   */
  public DNASequence(String givenData)
  {
    data = givenData;
    
  }
  
  /**
   * Returns a String representing the data for this
   * DNASequence.
   * @return
   *   the characters in this DNASequence
   */
  public String toString()
  {
	  
    return data;
  }
  
  /**
   * Determines whether all characters in this sequence
   * are valid ('A', 'G', 'C', or 'T' in uppercase only).
   * @return
   *   true if all characters are valid, false otherwise
   */
  public boolean allValid()
  {
	  for(int  i=0; i < data.length(); i++) {
		  char c = data.charAt(i);
		  if( c != 'A' && c!= 'G' && c != 'C' && c != 'T') {
			  return false;
		  }

	  }
return true;    // TODO
    
  }
  
  public static void main(String args[]) {
	  

	  DNASequence test = new DNASequence("AGAGCAT");
	  DNASequence test2 = new DNASequence("TCAT");
	  String countBonds = test.findBondsWithShift(test2, 0);
	  
	  boolean ans = test.isSubsequence(new DNASequence("CAAT"));
	  
	  
	  
	  

	  
	  System.out.println(ans);
	  
  }

  /**
   * Removes all invalid characters from this DNASequence.  For example, 
   * if this object's data is the string <code>"TaGxy*!  Cz"</code>, 
   * after calling this method, the data is <code>"TGC"</code>.
   */
  public void fix()
  {
	  
	  StringBuilder sb = new StringBuilder(); //has all valid characters in the right order
	  for(int  i = 0; i < data.length(); i++) {
		  char c = data.charAt(i);
		  if( c == 'A' || c== 'G' || c == 'C' || c == 'T') {
			sb.append(c);
		  }  
	  }
	
	  data = sb.toString();
	  

          }
  
  
  /**
   * Determines whether the given sequence is a subsequence
   * of this one.  A string t is a subsequence of another
   * string s if all characters of t can be found in s in the
   * same order.  Equivalently, string t is a subsequence of s
   * if t can be obtained by deleting some of the characters of s.
   * Invalid characters in the given string are ignored.
   * <p>
   * For example "TxxAA" is a subsequence of "CTyyGCACA" but 
   * not of "CAAT" nor of "TA".
   * @param other
   *   the given DNASequence
   * @return
   *   true if the given sequence is a subsequence of this one, 
   *   false otherwise
   */
  public boolean isSubsequence(DNASequence other)
  {
	  
			other.fix();

			String sub = other.data;
			int start = 0;
			for (int i = 0; i < sub.length(); i++) {

				int x = data.indexOf(sub.charAt(i), start);
				if (x == -1) {
					return false;
				} else {
					start = x + 1;
				}
			}
			// indexOff
			return true;
		
  }
  
  /**
   * Returns true if the two characters are complementary
   * ('A' with 'T' or 'C' with 'G').
   * @param c1
   *   potential character for a base pair
   * @param c2
   *   potential character for a base pair
   * @return
   *   true if the two characters are 'A' and 'T' or 'C' and 'G';
   *   false otherwise
   */
  public boolean willBond(char c1, char c2)
  {
	 
	  if((c1 == 'A' && c2 == 'T') || (c1 == 'C' && c2 == 'G') || (c1 == 'T' && c2 == 'A') || (c1 =='G' && c2 == 'C')) {
	  
	  return true;
	  }
    // TODO
    return false;
  }
    
  /**
   * Replaces this object's data with its complement;
   * that is, 'A' is replaced with 'T' and so on.
   * Invalid characters are not modified.
   * For example, if the data for this sequence is "AGTT", after
   * this method is called the data would be "TCAA".  
   */
  public void complement()
  {
	  data = data.replaceAll("A", "L");
	  data = data.replaceAll("G","M");
	  data = data.replaceAll("C", "N");
	  data = data.replaceAll("T", "O");
	  
	  data = data.replaceAll("L","T");
	  data = data.replaceAll("M","C");
	  data = data.replaceAll("N", "G");
	  data = data.replaceAll("O", "A");
    // TODO
  }
  
  /**
   * Returns the maximum possible number of bonds that can be formed
   * with this sequence when the given sequence is shifted left or 
   * right by any amount.
   * @param other
   *   the DNASequence to align with this one
   * @return
   *   maximum possible number of bonds 
   */
  public int findMaxPossibleBonds(DNASequence other)
  {
	  int count = 0;
	  int main = 0;
	  int i = data.length()-other.data.length();
	  if(i > 0)
		  i = i* (-1);
	  for (int j =i; j< data.length(); j++) {
		  count = countBondsWithShift(other, j);
		  if(count > main) {
			  main = count;
		  }
	  }
	  return main;
  
   
  }
  
  /**
   * Returns the number of bonds that are formed with this sequence
   * when the given sequence is shifted right by the given number
   * of spaces (where a negative number represents a left shift).
   * Neither this sequence nor the given sequence is modified.
   * @param other
   *   the DNASequence to align with this one
   * @param shift
   *   number of spaces to the right that the other sequence is shifted
   * @return
   *   number of bonds formed when the given sequence is 
   *   aligned with this one, with the given shift
   */
  public int countBondsWithShift(DNASequence other, int shift)
  {
	int countBonds = 0;
	
	String x = "";
	
	char n = 0;
	char m = 0;
	int i = 0;
	/**
	if(shift < 0) {
		i = shift*(-1);
	}
		shift = 0;
	 
	for(int u = 0; u < shift; u++) {
	if(shift >= data.length()) {
			break;
			**/
	if (shift > 0) {
		while(shift > 0) {
			x += " ";
			shift --;
			
			
		
		}
		x = x + other.data.substring(0); //add spaces 
		
	}
	else {
		x = other.data.substring(shift * (-1)); //removing characters
	}
		
		int l = 0;
	while (l < data.length() && l < x.length())  {
		char a = data.charAt(l);
		char b = x.charAt(l);
		if (willBond(a,b)) {
			countBonds+=1;
		}
		l++;
		
	}
		
	
		
		return countBonds;
	}
		
    

	

		
	
  
	  
  
  
	  
    // TODO
  
  
  
  /**
   * Returns a string showing which characters in this sequence
   * are bonded when the given sequence is shifted right by the given number
   * of spaces (where a negative number represents a left shift).
   * Non-matching characters are shown as dashes.  For example,
   * if this sequence is "ATATGC" and the given sequence is "TCC",
   * shifted right by 2, then this method returns "--A-G-".
   * Neither this sequence nor the given sequence is modified.
   * @param other
   *   the sequence to which this one is being matched
   * @param shift
   *   the number of spaces the other sequence is shifted to the right
   * @return
   *   string indicating where matches occur
   */
  public String findBondsWithShift(DNASequence other, int shift)
  {
	  String countBonds = "";
		char n = 0;
		char m = 0;
		int i = 0;
		if(shift < 0) {
			i = shift*(-1);
			shift = 0;
		}
		for(int u = 0; u < shift; u++) {
			countBonds += "-";
		}
		for(int v = i; v < other.data.length(); v++) {
			if(shift >= data.length()) {
				break;
				
			}
			m = data.charAt(shift);
			n = other.data.charAt(v);
			if(m == 'A' && n =='T') {
				countBonds+=data.charAt(shift);
			}
			else if( m == 'T' && n =='A') {
				countBonds+=data.charAt(shift);
			}
			else if(m == 'G' && n == 'C') {
				countBonds+=data.charAt(shift);
			}
			else if(n == 'C' && n == 'G' ) {
				countBonds+=data.charAt(shift);
			}
			else
				countBonds+="-";
			shift +=1;
			}
		int left = data.length() - countBonds.length();
			if(left > 0) {
				for(int w = 0; w < left; w++) {
					countBonds += "-";
				}
			
			
				
    // TODO
   
			}
			return countBonds;
  }
 
}





