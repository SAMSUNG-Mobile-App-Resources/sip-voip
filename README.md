# Extensions to a VoIP application based on the Session Initiation Protocol (SIP)
This code was developed by a team of four for a class on <b>Software Engineering</b> at <b>National Technical University of Athens</b>. The members of the team are:
- <a href="https://github.com/abenetopoulos">Achilles Benetopoulos</a>
- Marialena Fragkaki
- Giorgos Pantazopoulos
- <a href="https://github.com/manosth">Emmanouil Theodosis</a>

Our team was given an existing codebase of a working VoIP application based on the Session Initiation Protocol (SIP) and was tasked with implementing certain extensions. In particular, the extensions were:
- implement a way for users to register to the system, as there was only the option of logging in.
- implement a block and a forward feature.
- implement a databse where users are stored, so they are able to log in in the future.
- implement a billing system.

Also included are the Software Requirements Specifications Document (SRS) which describes the functional and non-functional requirements of the system, and the Software Design Document (SDD) which describes the architectural design of the system and a detailed design of the extensions, both in Greek. Do note that these documents were drafted <b>during the class<b>, before the implementation of the system, <b>and therefore may or may not reflect the actual implementation<b>.

## Extensions outline
### Registration
Running the provided application would prompt the user to log in using their credentials, or cancel the application. We added another button that replaces the current window with a registration form.
<br>
<br>
After completing the form, a check is made as to whether the inputs in each field are acceptable. If the user-entered inputs do not comply with the specifications, then the corresponding fields are flushed and theis labels are turned red.
<br>
<br>
After a successful registration, the user is automatically logged in and their credentials are stored in the databse.

### Block and Forwarding
The provided application would allow users to make calls to other users, and hang up. We added additionals buttons supporting call blocking and forwarding. In particular, two buttons were added to support each feature: one button to block (forward to) a user, and one to unblock (unforward).
<br>
<br>
The buttons for the positive action provide a dropdown list of the users in the database, and the active user can decide which user to block (forward to). The negative action buttons again provide a list with the blocked (forwarded) users, and the active user can decide which user to unblock (unforward to).

### Database
We created a database to store the users' credential. In particular, a username and the hash of the user's desired password are stored.
<br>
<br>
For the sake of simplicity, and because the purpose of the project was to familiarize ourselves with Java classes, we decided not to implement an actual database, but rather create a Java class and serialize it. Even though this approach is definitely not recommended for a commercial application, it allowed us to experiment and make on-the-fly design decisions that a rigid schema might not have.

### Billing System
We created a billing system for the users. During registration, a user chooses a billing policy, and when they are making calls they are billed according to their preferred policy.
<br>
<br>
There is also a button to add balance to a user's account. The user is billed the appropriate amount when the call ends.

## Implementation
The project was solely developed in Java. We made extensive use of the OOP paradigms in Java, and also used Java Swing in order to create the Graphical User Interface (GUI).
<br>
<br>
Finally, our team employed the Session Initiation Protocol (SIP), as defined by the Internet Engineering Task Force (IETF) in <a href="https://www.ietf.org/rfc/rfc3261.txt">RFC 3261</a>, to initiate and manage the connection between users.
