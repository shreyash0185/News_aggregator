<details> <summary>Authentication Flow</summary>
🧭 Authentication & Registration Flow – Visualized
plaintext
Copy
Edit
[User Registration Request]
         |
         v
[UserDTO Received in Controller]
         |
         v
[AuthenticationService.registerUser()]
         |
         v
[User Created with Encoded Password + Role + isEnabled(false)]
         |
         v
[User Saved in DB (UserRepository)]
         |
         v
[Verification Token Generated]
         |
         v
[Saved via VerificationTokenRepository]
         |
         v
[Email Sent (Not in code yet) → User clicks link]
         |
         v
[GET /verifyRegistration?token=abc123]
         |
         v
[AuthenticationService.verifyRegistration()]
         |
         v
[If Valid → enableUser()]
         |
         v
[Set user.setEnabled(true)]
         |
         v
[UserRepository.save(user)]
         |
         v
[VerificationTokenRepository.delete(token)]

🔐 Login & Token Generation Flow

[User Login Request]
|
v
[AuthenticationService.authenticateUser(username, password)]
|
v
[Find User by Username from DB]
|
+---> [If not found → throw UsernameNotFoundException]
|
+---> [If not enabled → throw IllegalStateException]
|
v
[Match Password using PasswordEncoder]
|
+---> [If password invalid → throw IllegalArgumentException]
|
v
[JWT Token Generated using JwtUtility.generateToken()]
|
v
[Return JWT → Client uses in Authorization: Bearer <token>]


🔒 Protected Endpoint Access Flow

[Client Sends Request with JWT in Authorization Header]
|
v
[JwtFilter Intercepts → JwtUtility.validateToken()]
|
+---> [If invalid → reject with 401]
|
v
[Extract UserDetails from Token]
|
v
[Set SecurityContext with Authenticated User]
|
v
[Proceed to Controller / Endpoint]
