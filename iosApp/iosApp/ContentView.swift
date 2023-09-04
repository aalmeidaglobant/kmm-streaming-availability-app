import SwiftUI
import shared

struct ContentView: View {
 @ObservedObject private(set) var viewModel: ViewModel

     var body: some View {
         NavigationView {
             listView()
             .navigationBarTitle("Streaming Shows")
             .navigationBarItems(trailing:
                 Button("Reload") {
                     self.viewModel.loadShows(forceReload: true)
             })
         }
     }

     private func listView() -> AnyView {
         switch viewModel.shows {
         case .loading:
             return AnyView(Text("Loading...").multilineTextAlignment(.center))
         case .result(let shows):
             return AnyView(List(shows) { show in
                 ShowRow(show: show)
             })
         case .error(let description):
             return AnyView(Text(description).multilineTextAlignment(.center))
         }
     }
 }

extension ContentView {
    enum LoadableShows {
        case loading
        case result([Show])
        case error(String)
    }

    @MainActor
       class ViewModel: ObservableObject {
           let sdk: MoviesSDK
           @Published var shows = LoadableShows.loading

           init(sdk: MoviesSDK) {
               self.sdk = sdk
               self.loadShows(forceReload: false)
           }

           func loadShows(forceReload: Bool) {
               Task {
                   do {
                       self.shows = .loading
                       let shows = try await sdk.getShows(forceReload: forceReload)
                       self.shows = .result(shows)
                   } catch {
                       self.shows = .error(error.localizedDescription)
                   }
               }
           }
       }
}

extension Show: Identifiable { }
