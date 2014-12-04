peak-Traffic
============

Problem Wording: https://codeeval.com/public_sc/49/

To execute the build.xml script

peakTraffic>ant

  a) Put the files to be tested into peakTraffic/tests folder.

  b) Output will be always printed on console.

  c) (Optional) To validate the results, insert the output expected in a file into peakTraffic/testsOutput.

Each file in (a) will be validated against the same filename located in (c) 

- peakTraffic/tests/test1.txt validated against peakTraffic/testsOutput/test1.txt
- peakTraffic/tests/test2.txt validated against peakTraffic/testsOutput/test2.txt

A variant of Bron-Kerbosch (Tomita et al) has been used:

	function Tomita(R, P, X)

		  if P and X are both empty then

			report R as a maximal clique

		  end if    

		choose the pivot vertex u in P union X as the vertex with the highest number of neighbors in P

		for each vertex v in P \ N(u) do

				Tomita(R union v, P intersection N(v), X intersection N(v))

		P =  P \ v

		X =  X union v

		end 

	end function  
