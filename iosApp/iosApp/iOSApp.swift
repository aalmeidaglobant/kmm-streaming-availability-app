import SwiftUI
import shared

@main
struct iOSApp: App {
    let sdk = MoviesSDK(databaseDriverFactory: DriverFactory())
       var body: some Scene {
           WindowGroup {
               ContentView(viewModel: .init(sdk: sdk))
           }
       }
}
