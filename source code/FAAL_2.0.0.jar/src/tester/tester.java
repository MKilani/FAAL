/* 
 * Copyright 2018 Marwan Kilani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package tester;

import java.util.ArrayList;
import java.util.List;

import faal.*;

public class tester {

	public static void main(String[] args) throws Exception {
		
		
		String word_1 = "hɐ￤kːe￤kʲːɯː";
		String word_2 = "pek￤çj͜ʌɭ￤gu";
		
	
		List<Alignment> Results_matcher = new ArrayList <>();
				
		
		Results_matcher = FAAL.align(word_1, word_2, true);
		
		
		
		String pair_prov = "";
		String res_A_prov = "";
		String res_B_prov = "";
		
		pair_prov = Results_matcher.get(0).getWord1_WithDiacritics() + " == " + Results_matcher.get(0).getWord2_WithDiacritics() ;
		res_A_prov = (String) Results_matcher.get(0).getWord1_WithoutDiacritics();
		res_B_prov = (String) Results_matcher.get(0).getWord2_WithoutDiacritics();
		
		System.out.println(pair_prov);
		System.out.println(res_A_prov);
		System.out.println(res_B_prov);
		System.out.println(Results_matcher.get(0).getGlobalSimilarityScore());
		System.out.println(Results_matcher.get(0).getCorrectedGlobalSimilarityScore());


		System.out.println(Results_matcher.get(0).getPhoneticPairs() );
		System.out.println(Results_matcher.get(0).getNrAttestationsPhoneticPairs() );
		
	   
		
		}
}
