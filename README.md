# TestAssignment
I add the README after the deadline. I think it's better to do it, even after the deadline, than not to do it at all.

I fixed the 2 bugs that made the test from the example fail, took out the constants and added a few decorative changes.
The biggest change I made is that I made the `Car` value of `energy` not drop below 0, even with large `energyUsage`. To do this, I allowed the `Car` to stop for more than one refueling. First, I sorted the `Station` by their location. Then I did two binary searches to find where in the list of `Station` are the beginning and end of the path. I then go through that segment, adding refueling stations to the path if there is not enough `energy` to get to the next one. If even in this way it is not possible to reach the end of the path, I display the appropriate message and the car remains in place.

I also added tests to account for the changes I made.

## Requirements
- Java 11
- Maven