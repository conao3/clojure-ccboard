{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";
    flake-parts.url = "github:hercules-ci/flake-parts";
    treefmt-nix.url = "github:numtide/treefmt-nix";
  };

  outputs = inputs @ { flake-parts, ... }:
    flake-parts.lib.mkFlake { inherit inputs; } {
      systems = [ "x86_64-linux" "aarch64-darwin" ];
      imports = [
        inputs.treefmt-nix.flakeModule
      ];
      perSystem =
        { pkgs
        , ...
        }:
        let
          jdk = pkgs.graalvmPackages.graalvm-ce;
          nodejs = pkgs.nodejs_26;
          clojure = pkgs.clojure.override { inherit jdk; };
          pnpm = pkgs.pnpm.override { nodejs-slim = pkgs.nodejs-slim_26; };
        in
        {
          devShells.default = pkgs.mkShell {
            packages = with pkgs; [
              jdk
              clojure
              clj-kondo
              nodejs
              pnpm
            ];
          };
          treefmt.config = {
            projectRootFile = "flake.nix";
            programs.nixpkgs-fmt.enable = true;
          };
        };
    };
}
